package com.proxy.ip.common;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class ExcutorConfig {
	private static Logger	logger	= LoggerFactory.getLogger(ExcutorConfig.class);

	@Autowired
	ExcutorProperties		properties;

	@Bean
	public Executor asyncServiceExecutor() {
		logger.info("start executor -->");
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 设置核心线程数
		executor.setCorePoolSize(properties.getCorePoolSize());
		// 设置最大线程数
		executor.setMaxPoolSize(properties.getMaxPoolSize());
		// 设置队列大小
		executor.setQueueCapacity(properties.getQueueCapacity());
		// 配置线程池的前缀
		executor.setThreadNamePrefix(properties.getThreadNamePrefix());
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 设置空闲时间
		executor.setKeepAliveSeconds(properties.getKeepAliveSeconds());
		// 进行加载
		executor.initialize();
		return executor;
	}

}