package com.example.demo.service.loadFile;

import java.io.File;

import org.chuxue.application.common.base.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.config.UserConfig;
import com.example.demo.po.SysLoadFileInfo;
import com.example.demo.vo.SysLoadFileColsMappingVo;

/**
 * 文件名 ： CsvFileRead.java
 * 包 名 ： com.example.demo.service.readFile
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年8月1日 下午5:39:39
 * 版 本 ： V1.0
 */
@Component
@Order(23)
public class CsvFileLoad {
	
	@Autowired
	UserConfig userConfig;
	
	/**
	 * 方法名： readFileCsv
	 * 功 能： 读取文件
	 * 参 数： @param vo
	 * 参 数： @return
	 * 返 回： SysLoadFileInfoVo
	 * 作 者 ： Administrator
	 * @throws
	 */
	public void loadFileCsv(SysLoadFileColsMappingVo vo) {
		SysLoadFileInfo info = vo.getFileInfo();
		String path = info.getPath();
		File file = new File(path);
		if (file.exists()) {
			fileLoad(file, vo);
		} else {
			throw new BaseException(-1, "没有找到文件");
		}
	}
	
	/**
	 * 方法名： fileReadConfTopTen
	 * 功 能： 没有配置的直接读取前十条
	 * 参 数： @param file
	 * 参 数： @param vo
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	private void fileLoad(File file, SysLoadFileColsMappingVo vo) {
		
	}
	
}
