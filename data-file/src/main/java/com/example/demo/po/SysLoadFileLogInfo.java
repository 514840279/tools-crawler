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
 * @文件名 SysLoadFileLogInfo.java
 * @包名 com.example.demo.po
 * @描述 `sys_load_file_log_info`的实体类
 * @时间 2022年07月28日 15:54:33
 * @author
 * @版本 V1.0
 */
@Setter
@Getter
@TableName(value = "`sys_load_file_log_info`")
public class SysLoadFileLogInfo extends MybatisBaseEntity implements Serializable {
	private static final long	serialVersionUID	= 1L;

	// 文件名
	@TableField(value = "file_uuid")
	private String				fileUuid;

	// 成功行
	@TableField(value = "complate_rows")
	private Long				complateRows;

	// 错误行
	@TableField(value = "error_rows")
	private Long				errorRows;

	// 错误文件位置
	@TableField(value = "error_file")
	private String				errorFile;

	// 错误文件位置
	@TableField(value = "error_message")
	private String				errorMessage;

	// 状态
	@TableField(value = "run_state")
	private String				runState;

	/**
	 * 构造方法：
	 * 描 述： 默认构造函数
	 * 参 数：
	 * 作 者 ：
	 * @throws
	 */
	public SysLoadFileLogInfo() {
	}

	/**
	 * 构造方法：
	 * 描 述： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param fileUuid
	 * 作 者 ： Administrator
	 * @throws
	 */
	public SysLoadFileLogInfo(String fileUuid) {
		this.fileUuid = fileUuid;
	}

	/**
	 * 构造方法：
	 * 描 述： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param fileUuid
	 * 参 数： @param complateRows
	 * 参 数： @param errorRows
	 * 参 数： @param errorFile
	 * 参 数： @param runState
	 * 作 者 ： Administrator
	 * @throws
	 */
	public SysLoadFileLogInfo(String fileUuid, Long complateRows, Long errorRows, String errorFile, String runState) {
		this.uuid = UUID.randomUUID().toString();
		this.createTime = new Date();
		this.deleteFlag = 0;
		this.fileUuid = fileUuid;
		this.complateRows = complateRows;
		this.errorRows = errorRows;
		this.errorFile = errorFile;
		this.runState = runState;
	}

}
