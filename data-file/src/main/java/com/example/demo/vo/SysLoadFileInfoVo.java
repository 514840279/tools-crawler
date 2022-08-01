package com.example.demo.vo;

import java.util.List;
import java.util.Map;

import com.example.demo.po.SysLoadFileColsInfo;
import com.example.demo.po.SysLoadFileInfo;

import lombok.Getter;
import lombok.Setter;

/**
 * 文件名 ： SysLoadFileInfoVo.java
 * 包 名 ： com.example.demo.vo
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年8月1日 下午5:12:20
 * 版 本 ： V1.0
 */
@Setter
@Getter
public class SysLoadFileInfoVo {
	
	private SysLoadFileInfo				info;
	private List<SysLoadFileColsInfo>	columns;
	private List<Map<String, String>>	datas;
}
