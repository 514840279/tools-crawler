package com.example.demo.po;

import java.io.Serializable;

import org.chuxue.application.common.base.MybatisBaseEntity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @文件名 SysLoadFileColsInfo.java
 * @包名 com.example.demo.po
 * @描述 `sys_load_file_cols_info`的实体类
 * @时间 2022年07月28日 15:54:33
 * @author
 * @版本 V1.0
 */
@Setter
@Getter
@TableName(value = "`sys_load_file_cols_info`")
public class SysLoadFileColsInfo extends MybatisBaseEntity implements Serializable {
	private static final long	serialVersionUID	= 1L;

	// 文件id
	@TableField(value = "file_uuid")
	private String				fileUuid;

	// 字段名
	@TableField(value = "column_name")
	private String				columnName;

	@TableField(value = "column_desc")
	private String				columnDesc;

	// 字段类型，默认是text
	@TableField(value = "column_type")
	private String				columnType;

	// 字段长度
	@TableField(value = "column_length")
	private Integer				columnLength;

	/**
	 * 构造方法：
	 * 描 述： 默认构造函数
	 * 参 数：
	 * 作 者 ：
	 * @throws
	 */
	public SysLoadFileColsInfo() {
	}

}
