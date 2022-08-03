package com.example.demo.po;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.chuxue.application.common.base.MybatisBaseEntity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @文件名 SysDbmsTabsColsInfo`.java
 * @包名 com.example.demo.po
 * @描述 `sys_dbms_tabs_cols_info`的实体类
 * @时间 2022年08月01日 15:15:09
 * @author
 * @版本 V1.0
 */
@Setter
@Getter
@TableName(value = "`sys_dbms_tabs_cols_info`")
public class SysDbmsTabsColsInfo extends MybatisBaseEntity implements Serializable {
	private static final long	serialVersionUID	= 1L;

	//
	@TableField(value = "cols_align")
	private String				colsAlign;

	//
	@TableField(value = "cols_default")
	private String				colsDefault;

	//
	@TableField(value = "cols_desc")
	private String				colsDesc;

	//
	@TableField(value = "cols_length")
	private Integer				colsLength;

	//
	@TableField(value = "cols_name")
	private String				colsName;

	//
	@TableField(value = "cols_switchable")
	private Boolean				colsSwitchable;

	//
	@TableField(value = "cols_type")
	private String				colsType;

	//
	@TableField(value = "cols_valign")
	private String				colsValign;

	//
	@TableField(value = "cols_width")
	private Integer				colsWidth;

	//
	@TableField(value = "data_precision")
	private Integer				dataPrecision;

	//
	@TableField(value = "data_scale")
	private Integer				dataScale;

	//
	@TableField(value = "data_type")
	private String				dataType;

	//
	@TableField(value = "index_code")
	private String				indexCode;

	//
	@TableField(value = "nullable")
	private String				nullable;

	//
	@TableField(value = "tabs_uuid")
	private String				tabsUuid;

	//
	@TableField(value = "user_icon")
	private String				userIcon;

	/**
	 * 构造方法：
	 * 描 述： 默认构造函数
	 * 参 数：
	 * 作 者 ：
	 * @throws
	 */
	public SysDbmsTabsColsInfo() {
	}
	
	/**
	 * 构造方法：
	 * 描 述： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param colsDesc
	 * 参 数： @param colsName
	 * 参 数： @param colsType
	 * 参 数： @param tabsUuid
	 * 作 者 ： Administrator
	 * @throws
	 */
	public SysDbmsTabsColsInfo(String colsDesc, String colsName, String colsType, String tabsUuid) {
		this.uuid = UUID.randomUUID().toString();
		this.deleteFlag = 0;
		this.colsLength = 255;
		this.colsWidth = 150;
		this.colsSwitchable = true;
		this.colsDesc = colsDesc;
		this.colsName = colsName;
		this.colsType = colsType;
		this.tabsUuid = tabsUuid;
		this.createTime = new Date();
	}
	
}
