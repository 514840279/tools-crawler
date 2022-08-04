package com.example.demo.vo;

import java.util.List;

import com.example.demo.po.SysDbmsTabsTableInfo;
import com.example.demo.po.SysLoadFileColsMapping;
import com.example.demo.po.SysLoadFileInfo;

import lombok.Getter;
import lombok.Setter;

/**
 * 文件名 ： SysLoadFileColsMappingVo.java
 * 包 名 ： com.example.demo.vo
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年8月4日 下午2:45:20
 * 版 本 ： V1.0
 */
@Setter
@Getter
public class SysLoadFileColsMappingVo {
	private SysLoadFileInfo					fileInfo;
	private SysDbmsTabsTableInfo			tableInfo;
	private List<SysLoadFileColsMapping>	mappings;
}
