package com.proxy.ip.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "user")
@Data
public class UserConfig {

	// 总开关
	private Boolean			start			= false;
	// 免费代理
	private Boolean			startFree		= false;
	// 免费代理
	private Boolean			startKingdaili	= false;
	
	// 每次取任务数量
	private Integer			pageSize		= 100;
	// 从哪里开始取
	private Integer			startNum		= 0;
	// 删除任务数据开关 减少剩余任务数量
	private Boolean			startDel		= false;

	// 库中最小保留可用ip
	private Integer			minSize			= 100;
	// ip有效时间，库中ip从入库开始算最长保留时间 /s
	private Long			longTime		= 240l;

	// 获取ip：port的地址访问间隔 /ms
	private Long			urlTime			= 5000l;

	private Boolean			checkIp			= false;
	private String			checkUrl		= "https://www.baidu.com";
	// ip:port 类型的代理地址服务
	private List<String>	urls			= new ArrayList<>();
	
}
