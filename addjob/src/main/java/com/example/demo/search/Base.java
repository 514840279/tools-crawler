package com.example.demo.search; //

import lombok.Getter; //
import lombok.Setter; //

/**
 * 文件名 ： Base.java
 * 包 名 ： com.example.demo.search
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年6月24日 下午5:46:10
 * 版 本 ： V1.0
 */
@Setter
@Getter
public class Base {

	private String	creditCode;				// 统一社会信用代码 varchar
	private String	companyName;			// 企业名称 varchar
	private String	registrationNumber;		// 注册号 varchar
	private String	lawPerson;				// 法定代表人 varchar
	private String	companyType;			// 类型 varchar
	private String	registrationDate;		// 成立日期 varchar
	private String	registrationCapital;	// 注册资本 varchar
	private String	approvalDate;			// 核准日期 varchar
	private String	businessTermStart;		// 营业期限自 varchar
	private String	businessTermEnd;		// 营业期限至 varchar
	private String	registrationAuthority;	// 登记机关 varchar
	private String	businessStatus;			// 登记状态 varchar
	private String	registrationAddress;	// 住所 varchar
	private String	businessScope;			// 经营范围 varchar
	private String	historicalName;			// 历史名称 varchar
	private String	unique;					// 唯一 varchar
	private String	inserttime;				// timestamp
	private String	keyAreas;				// 重点领域 varchar
	private String	exceptionType;			// 异常类型 varchar
	private String	exceptionDetails;		// 异常信息 varchar
}
