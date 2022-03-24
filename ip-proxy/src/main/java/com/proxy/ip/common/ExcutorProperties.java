package com.proxy.ip.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 文件名 ： ExcutorProperties.java
 * 包 名 ： com.proxy.ip.common
 * 描 述 ： 线程池默认配置参数
 * 作 者 ： Administrator
 * 时 间 ： 2022年3月23日 上午9:40:37
 * 版 本 ： V1.0
 */
@Component
@ConfigurationProperties(prefix = "spring.excutor")
@Data
public class ExcutorProperties {
	private Integer	corePoolSize		= 200;
	private Integer	maxPoolSize			= 400;
	private Integer	queueCapacity		= 1000;
	private String	threadNamePrefix	= "thread-service";
	private Integer	keepAliveSeconds	= 5;

}
