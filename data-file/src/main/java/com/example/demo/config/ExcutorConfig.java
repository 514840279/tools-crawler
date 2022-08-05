package com.example.demo.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class ExcutorConfig implements AsyncConfigurer {

	private static Logger			logger	= LoggerFactory.getLogger(ExcutorConfig.class);

	@Autowired
	private TaskExecutionProperties	taskExecutionProperties;

	@Bean
	public Executor asyncServiceExecutor() {
		logger.info("start asyncServiceExecutor -->");
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 设置核心线程数
		executor.setCorePoolSize(taskExecutionProperties.getPool().getCoreSize());
		// 设置最大线程数
		executor.setMaxPoolSize(taskExecutionProperties.getPool().getMaxSize());
		// 设置队列大小
		executor.setQueueCapacity(taskExecutionProperties.getPool().getQueueCapacity());
		// 配置线程池的前缀
		executor.setThreadNamePrefix(taskExecutionProperties.getThreadNamePrefix());
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 进行加载
		executor.initialize();
		return executor;
	}

}