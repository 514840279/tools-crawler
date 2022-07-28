package com.example.demo.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import org.chuxue.application.common.base.MybatisBaseEntity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * @文件名 SysLoadFileInfo.java
 * @包名 com.example.demo.po
 * @描述 `sys_load_file_info`的实体类
 * @时间 2022年07月28日 15:54:33
 * @author
 * @版本 V1.0
 */
@Setter
@Getter
@TableName(value = "`sys_load_file_info`")
public class SysLoadFileInfo extends MybatisBaseEntity implements Serializable {
	private static final long	serialVersionUID	= 1L;

	// 路径
	@TableField(value = "path")
	private String				path;

	// 文件名
	@TableField(value = "file_name")
	private String				fileName;

	// 文件类型
	@TableField(value = "file_type")
	private String				fileType;

	// 文件大小
	@TableField(value = "file_size")
	private String				fileSize;

	// 状态
	@TableField(value = "file_state")
	private String				fileState;

	// 字符集
	@TableField(value = "characterset")
	private String				characterset;

	// 跳过
	@TableField(value = "skip")
	private Integer				skip;

	// 分割符号
	@TableField(value = "separator")
	private String				separator;

	// 限定符号，字段包围的符号
	@TableField(value = "enclosed")
	private String				enclosed;

	// 首行是标题
	@TableField(value = "has_head")
	private String				hasHead;

	// 事务提交的限制
	@TableField(value = "rows")
	private Integer				rows;

	/**
	 * 构造方法：
	 * 描 述： 默认构造函数
	 * 参 数：
	 * 作 者 ：
	 * @throws
	 */
	public SysLoadFileInfo() {
	}

	/**
	 * 构造方法：
	 * 描 述： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param path
	 * 参 数： @param fileName
	 * 参 数： @param fileSize
	 * 作 者 ： Administrator
	 * @throws
	 */
	public SysLoadFileInfo(String uuid, String path, String fileName, Long fileSize) {
		this.uuid = uuid == null ? UUID.randomUUID().toString() : uuid;
		this.path = path;
		this.fileName = fileName;
		BigDecimal size = BigDecimal.valueOf(fileSize);
		if (fileSize / 1024 < 1) {
			this.fileSize = fileSize + "Byte";
		} else if (fileSize / 1024 / 1024 < 1) {
			this.fileSize = size.divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_DOWN) + "KB";
		} else if (fileSize / 1024 / 1024 / 1024 < 1) {
			this.fileSize = size.divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_DOWN).divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_DOWN) + "MB";
		} else if (fileSize / 1024 / 1024 / 1024 / 1024 < 1) {
			this.fileSize = size.divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_DOWN).divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_DOWN).divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_DOWN) + "GB";
		}
	}
	
	/**
	 * 构造方法：
	 * 描 述： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param absolutePath
	 * 作 者 ： Administrator
	 * @throws
	 */
	public SysLoadFileInfo(String path) {
		this.path = path;
	}

	/**
	 * 构造方法：
	 * 描 述： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param absolutePath
	 * 参 数： @param name
	 * 参 数： @param length
	 * 作 者 ： Administrator
	 * @throws
	 */
	public SysLoadFileInfo(String absolutePath, String name, long length) {
		this(null, absolutePath, name, length);
	}

}
