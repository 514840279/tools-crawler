package com.example.demo.service.loadFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.chuxue.application.common.base.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.config.UserConfig;
import com.example.demo.po.SysLoadFileInfo;
import com.example.demo.vo.SysLoadFileColsMappingVo;

/**
 * 文件名 ： TxtFileRead.java
 * 包 名 ： com.example.demo.service.readFile
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年8月1日 下午5:40:36
 * 版 本 ： V1.0
 */
@Component
@Order(24)
public class TxtFileLoad {

	@Autowired
	UserConfig userConfig;
	
	/**
	 * 方法名： readFileTxt
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param vo
	 * 参 数： @return
	 * 返 回： SysLoadFileInfoVo
	 * 作 者 ： Administrator
	 * @throws
	 */
	public void loadFileTxt(SysLoadFileColsMappingVo vo) {
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

	/**
	 * @param sysLoadFileInfo
	 * 方法名： readTxtFileTopData
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param file
	 * 参 数： @return
	 * 返 回： List<String>
	 * 作 者 ： Administrator
	 * @throws
	 */
	private List<String> readTxtFileTopData(File file, SysLoadFileInfo sysLoadFileInfo) {
		file.setReadable(true);// 设置可读
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = "";
		ArrayList<String> allString = new ArrayList<>();
		try {
			Integer arows = sysLoadFileInfo.getSkip();
			if (arows == null) {
				arows = 0;
			}
			int crows = 0;
			// 读取到的内容给line变量 只读十条 ，跳过前面
			while ((line = br.readLine()) != null && userConfig.getReadRows() > allString.size()) {
				crows += 1;
				if (crows > arows) {
					allString.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allString;
	}

}
