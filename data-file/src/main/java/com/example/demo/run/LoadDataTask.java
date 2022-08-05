package com.example.demo.run;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.po.RunState;
import com.example.demo.po.SysLoadFileLogInfo;
import com.example.demo.service.SysLoadFileLogInfoService;
import com.example.demo.service.loadFile.AsyncLoadFileDataService;

/**
 * 文件名 ： LoadFileTask.java
 * 包 名 ： com.example.demo.run
 * 描 述 ： 读取任务数据导入，导入表
 * 作 者 ： Administrator
 * 时 间 ： 2022年8月5日 下午1:59:01
 * 版 本 ： V1.0
 */
@Component
@Order(30)
public class LoadDataTask {

	@Autowired
	SysLoadFileLogInfoService	sysLoadFileLogInfoService;
	
	@Autowired
	AsyncLoadFileDataService	asyncLoadFileDataService;

	// 每天凌晨开始执行
//	@Scheduled(cron = "1 0 0 * * ? ")
	@Scheduled(fixedRate = 1000)
	public void run() {
		// 运行中
		QueryWrapper<SysLoadFileLogInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.in("run_state", RunState.STRAT, RunState.RUNNING);
		List<String> list = new ArrayList<>();
		list.add("run_state");
		queryWrapper.orderByDesc(list);
		list = new ArrayList<>();
		list.add("update_time");
		list.add("create_time");
		queryWrapper.orderByAsc(list);

		List<CompletableFuture<String>> alistCompletableFuture = new ArrayList<>();
		List<SysLoadFileLogInfo> listLog = sysLoadFileLogInfoService.list(queryWrapper);
		if (listLog != null && listLog.size() > 0) {
			for (SysLoadFileLogInfo sysLoadFileLogInfo : listLog) {
				//
				CompletableFuture<String> createOrder = asyncLoadFileDataService.executeAsync(sysLoadFileLogInfo);
				alistCompletableFuture.add(createOrder);
			}

			// 初始化需要得到的数组
			@SuppressWarnings("unchecked")
			CompletableFuture<String>[] array = new CompletableFuture[alistCompletableFuture.size()];
			
			// 使用for循环得到数组
			for (int i = 0; i < alistCompletableFuture.size(); i++) {
				array[i] = alistCompletableFuture.get(i);
			}
			
			// 等待所有任务都执行完
			CompletableFuture.allOf(array).join();

		}

	}
	
}
