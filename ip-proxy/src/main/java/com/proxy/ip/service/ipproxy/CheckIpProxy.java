package com.proxy.ip.service.ipproxy;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.proxy.ip.common.UserConfig;
import com.proxy.ip.dao.IpProxyInfoDao;
import com.proxy.ip.po.IpProxyInfo;

@Component
public class CheckIpProxy {
	
	@Autowired
	IpProxyInfoDao	ipProxyInfoDao;
	
	@Autowired
	UserConfig		userConfig;
	
	@Async
	public CompletableFuture<IpProxyInfo> check(IpProxyInfo info) {

		String ip = info.getIp();
		Integer port = info.getPort();
		// 查詢目的去重
		IpProxyInfo s = ipProxyInfoDao.findOneByIpAndPort(ip, port);
		if (s != null) {
//			return CompletableFuture.completedFuture(null);
			info = s;
			info.setDeleteFlag(0);
		}
		
		URL url = null;
		try {
			url = new URL(userConfig.getCheckUrl());
		} catch (MalformedURLException e) {
			System.out.println("url invalidate");
		}
		InetSocketAddress addr = null;
		addr = new InetSocketAddress(ip, port);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http proxy
		
		try {
			Long time1 = System.currentTimeMillis();
			URLConnection conn = url.openConnection(proxy);
			conn.setConnectTimeout(2000);
			conn.setReadTimeout(2000);
			conn.getInputStream();
			Long time2 = System.currentTimeMillis();
			info.setResponseTime((time2 - time1) / 1000.00);
			
		} catch (Exception e) {
			System.out.println("ip " + ip + " is not aviable");// 异常IP
			return CompletableFuture.completedFuture(null);
		}
		
		return CompletableFuture.completedFuture(info);
		
	}
	
}
