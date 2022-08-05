package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * 文件名 ： UserConfig.java
 * 包 名 ： com.example.demo.config
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年7月28日 下午1:36:20
 * 版 本 ： V1.0
 */
@Component
@ConfigurationProperties(prefix = "user")
@Order(1)
@Getter
@Setter
public class UserConfig {

	private Boolean	readfile	= true;
	private String	fileDir		= "import/";
	private String	errorDir	= "importError/";

	private Integer	readRows	= 10;
	private Long	commitRows	= 1000L;

}
