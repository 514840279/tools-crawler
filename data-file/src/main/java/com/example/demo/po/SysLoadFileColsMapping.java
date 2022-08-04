package com.example.demo.po;

import java.io.Serializable;

import org.chuxue.application.common.base.MybatisBaseEntity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @文件名 SysLoadFileColsMapping.java
 * @包名 com.example.demo.po
 * @描述 `sys_load_file_cols_mapping`的实体类
 * @时间 2022年07月28日 15:54:33
 * @author
 * @版本 V1.0
 */
@Setter
@Getter
@TableName(value = "`sys_load_file_cols_mapping`")
public class SysLoadFileColsMapping extends MybatisBaseEntity implements Serializable {
	private static final long	serialVersionUID	= 1L;

	// 文件id
	@TableField(value = "file_uuid")
	private String				fileUuid;

	// 字段id
	@TableField(value = "file_column_uuid")
	private String				fileColumnUuid;

	// 字段格式化
	@TableField(value = "column_format")
	private String				columnFormat;

	// 表id
	@TableField(value = "tabs_uuid")
	private String				tabsUuid;

	// 表字段
	@TableField(value = "tabs_column_uuid")
	private String				tabsColumnUuid;

	/**
	 * 构造方法：
	 * 描 述： 默认构造函数
	 * 参 数：
	 * 作 者 ：
	 * @throws
	 */
	public SysLoadFileColsMapping() {
	}

}
