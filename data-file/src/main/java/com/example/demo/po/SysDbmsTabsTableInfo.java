package com.example.demo.po;

import org.chuxue.application.common.base.MybatisBaseEntity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @文件名 SysDbmsTabsTableInfo.java
 * @包名 com.example.demo.po
 * @描述 `sys_dbms_tabs_table_info`的实体类
 * @时间 2022年08月01日 15:15:14
 * @author 
 * @版本 V1.0
 */
@Setter
@Getter
@TableName(value =  "`sys_dbms_tabs_table_info`")
public class SysDbmsTabsTableInfo extends MybatisBaseEntity implements Serializable {
	private static final long	serialVersionUID	= 1L;


	// 
	@TableField(value = "dissql")
	private  Integer 	dissql;

	// 
	@TableField(value = "jdbc_uuid")
	private  String 	jdbcUuid;

	// 
	@TableField(value = "tabs_desc")
	private  String 	tabsDesc;

	// 
	@TableField(value = "tabs_name")
	private  String 	tabsName;

	// 
	@TableField(value = "tabs_rows")
	private  Integer 	tabsRows;

	// 
	@TableField(value = "tabs_space")
	private  String 	tabsSpace;

	// 
	@TableField(value = "type_code")
	private  String 	typeCode;

	/**  
	*  构造方法： 
	*  描    述： 默认构造函数  
	*  参    数： 
	*  作    者 ：   
	*  @throws  
	*/
	public SysDbmsTabsTableInfo() {
		super();
	}



}
