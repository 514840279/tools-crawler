package com.proxy.ip.task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.proxy.ip.common.DateUtils;
import com.proxy.ip.common.UserConfig;
import com.proxy.ip.dao.IpProxyInfoDao;
import com.proxy.ip.po.IpProxyInfo;
import com.proxy.ip.service.ipproxy.CheckIpProxy;

@Component
@Order(30)
public class IPTask {
	private static final Logger	logger	= LoggerFactory.getLogger(IPTask.class);
	@Autowired
	UserConfig					userConfig;
	
	@Autowired
	IpProxyInfoDao				ipProxyInfoDao;

	@Autowired
	CheckIpProxy				checkIpProxy;
	
	//
	@Scheduled(cron = "0/30 0/1 * * * ? ")
	// @Scheduled(fixedRate = 1000)
	public void run() {
		logger.info("run {}", DateUtils.getDateTimeStr());
		// 判断总开关
		if (userConfig.getStart()) {
			// 判断有效IP量

			long startTime = System.currentTimeMillis(); // 获取开始时间
			Long time = startTime - userConfig.getLongTime() * 1000;
			Date d = new Date(time);
			SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<IpProxyInfo> list = ipProxyInfoDao.findList(userConfig.getMinSize(), myFormatter.format(d));
			if (list == null || list.size() < userConfig.getMinSize()) {
				// 第一个免费代理信息录入
				parseGit();
				
				// 第er个免费代理信息录入

			}
		}
	}

	private int parseGit() {
		// https://raw.githubusercontent.com/fate0/proxylist/master/proxy.list
		// 建立一个新的请求客户端
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 使用HttpGet方式请求网址
		HttpGet httpGet = new HttpGet("https://raw.githubusercontent.com/fate0/proxylist/master/proxy.list");
		// 获取网址的返回结果
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 获取返回结果中的实体
		HttpEntity entity = response.getEntity();

		// 将返回的实体输出
		try {
			String[] ss = EntityUtils.toString(entity).split("\n");
			List<IpProxyInfo> list = new ArrayList<>();
			// 创建异步信息
			List<CompletableFuture<IpProxyInfo>> alistCompletableFuture = new ArrayList<>();
			for (String string : ss) {
				JSONObject obj = JSONObject.parseObject(string);
				IpProxyInfo info = new IpProxyInfo(obj.getString("host"), obj.getInteger("port"), obj.getDouble("response_time"), obj.getString("country"), 0);
				Boolean a = true;
				// 去重
				for (IpProxyInfo info2 : list) {
					if (info.getIp().equals(info2.getIp()) && info.getPort().equals(info2.getPort())) {
						a = false;
						break;
					}
				}
				
				if (a) {
					list.add(info);
					// 驗證
					CompletableFuture<IpProxyInfo> createOrder = checkIpProxy.check(info);
					alistCompletableFuture.add(createOrder);
				}
			}
			
			// 创建结果信息
			list = new ArrayList<>();
			// 创建完成信息
			for (CompletableFuture<IpProxyInfo> completableFuture : alistCompletableFuture) {
				// 获取异步信息
				// 收集结果信息
				IpProxyInfo info = completableFuture.get();
				if (info != null) {
					list.add(info);
				}
			}
			if (list.size() > 0) {
				ipProxyInfoDao.saveAllAndFlush(list);
			}
			EntityUtils.consume(entity);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	@Scheduled(cron = "0 0 0/1 * * ? ")
	// @Scheduled(fixedRate = 1000)
	public void runDelete() {
		logger.info("runDelete {}", DateUtils.getDateTimeStr());
		ipProxyInfoDao.deleteAllByDeleteFlag(1);
	}
}