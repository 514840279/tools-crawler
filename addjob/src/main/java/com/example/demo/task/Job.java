package com.example.demo.task;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 文件名 ： Job.java
 * 包 名 ： com.example.demo.task
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年6月24日 下午2:32:43
 * 版 本 ： V1.0
 */
@Setter
@Getter
public class Job {
	
	private Integer	id;
	private String	regno;
	private String	companyName;
	private String	anCheYear;
	private Integer	flag;

	@DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date	insertTime;

}
