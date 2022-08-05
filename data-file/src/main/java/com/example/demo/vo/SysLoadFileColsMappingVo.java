package com.example.demo.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.po.SysDbmsTabsColsInfo;
import com.example.demo.po.SysDbmsTabsTableInfo;
import com.example.demo.po.SysLoadFileColsInfo;
import com.example.demo.po.SysLoadFileColsMapping;
import com.example.demo.po.SysLoadFileInfo;
import com.example.demo.po.SysLoadFileLogInfo;

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
	private SysLoadFileLogInfo					fileLog;
	private SysLoadFileInfo						fileInfo;
	private Map<Integer, SysLoadFileColsInfo>	fileColsInfos;
	private SysDbmsTabsTableInfo				tableInfo;
	private List<SysLoadFileColsMapping>		mappings;
	
	private String								sqlstr;
	
	/**
	 * 构造方法：
	 * 描 述： TODO(这里用一句话描述这个方法的作用)
	 * 参 数：
	 * 作 者 ： Administrator
	 * @throws
	 */
	public SysLoadFileColsMappingVo() {
	}
	
	/**
	 * 构造方法：
	 * 描 述： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param fileLog
	 * 参 数： @param fileInfo
	 * 参 数： @param fileColsInfos
	 * 参 数： @param tableInfo
	 * 参 数： @param mappings
	 * 作 者 ： Administrator
	 * @throws
	 */
	public SysLoadFileColsMappingVo(SysLoadFileLogInfo fileLog, SysLoadFileInfo fileInfo, List<SysLoadFileColsInfo> fileColsInfos, SysDbmsTabsTableInfo tableInfo, List<SysLoadFileColsMapping> mappings, List<SysDbmsTabsColsInfo> tableColInfo) {
		this.fileLog = fileLog;
		this.fileInfo = fileInfo;
//		fileColsInfos.sort((c1, c2) -> (c1.getSort() - c2.getSort()));
//		this.fileColsInfos = fileColsInfos;
		this.sqlstr = constructStr(tableInfo, tableColInfo, mappings);
		this.fileColsInfos = arrayToMap(fileColsInfos, tableColInfo, mappings);
		this.tableInfo = tableInfo;
		this.mappings = mappings;
	}
	
	/**
	 * 方法名： constructStr
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param tableInfo2
	 * 参 数： @param tableColInfo
	 * 参 数： @param mappings2
	 * 参 数： @return
	 * 返 回： String
	 * 作 者 ： Administrator
	 * @throws
	 */
	private String constructStr(SysDbmsTabsTableInfo tableInfo, List<SysDbmsTabsColsInfo> tableColInfo, List<SysLoadFileColsMapping> mappings) {
		StringBuilder sbBuilder = new StringBuilder();
		StringBuilder colstr = new StringBuilder();
		StringBuilder valstr = new StringBuilder();
		sbBuilder.append("insert into ");
		sbBuilder.append(tableInfo.getTabsName());
		sbBuilder.append("(");
		for (int i = 0; i < mappings.size(); i++) {
			SysLoadFileColsMapping mapping = mappings.get(i);
			
			for (SysDbmsTabsColsInfo tabsColsInfo : tableColInfo) {
				if (tabsColsInfo.getUuid().equals(mapping.getTabsColumnUuid())) {
					if (i > 0) {
						colstr.append(",");
						valstr.append(",");
					}
					colstr.append(tabsColsInfo.getColsName());
					valstr.append("?");
//					valstr.append(":" + tabsColsInfo.getColsName());
				}
			}
		}
		sbBuilder.append(colstr.toString());
		sbBuilder.append(") values(");
		sbBuilder.append(valstr.toString());
		sbBuilder.append(")");
		return sbBuilder.toString();
	}

	/**
	 * @param mappings2
	 * @param tableColInfo
	 * 方法名： arrayToMap
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param fileColsInfos2
	 * 参 数： @return
	 * 返 回： Map<Integer,SysLoadFileColsInfo>
	 * 作 者 ： Administrator
	 * @throws
	 */
	private Map<Integer, SysLoadFileColsInfo> arrayToMap(List<SysLoadFileColsInfo> fileColsInfos, List<SysDbmsTabsColsInfo> tableColInfo, List<SysLoadFileColsMapping> mappings) {
		if (fileColsInfos == null || fileColsInfos.size() == 0) {
			return null;
		}
		if (tableColInfo == null || tableColInfo.size() == 0) {
			return null;
		}
		if (mappings == null || mappings.size() == 0) {
			return null;
		}
		List<SysLoadFileColsInfo> te = new ArrayList<>();
		for (SysLoadFileColsMapping mapping : mappings) {
			for (SysDbmsTabsColsInfo tabsColsInfo : tableColInfo) {
				if (tabsColsInfo.getUuid().equals(mapping.getTabsColumnUuid())) {
					for (SysLoadFileColsInfo sysLoadFileColsInfo : fileColsInfos) {
						if (sysLoadFileColsInfo.getUuid().equals(mapping.getFileColumnUuid())) {
							//
							sysLoadFileColsInfo.setColumnDesc(tabsColsInfo.getColsName());
							te.add(sysLoadFileColsInfo);
							mapping.setTabsColumnUuid(tabsColsInfo.getColsName());
							mapping.setFileColumnUuid(sysLoadFileColsInfo.getColumnName());
						}
					}
					
				}
			}
		}

		Map<Integer, SysLoadFileColsInfo> map = new HashMap<>();
		for (SysLoadFileColsInfo sysLoadFileColsInfo : te) {
			map.put(sysLoadFileColsInfo.getSort(), sysLoadFileColsInfo);
		}
		return map;
	}
}
