package com.example.demo.run;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.config.UserConfig;
import com.example.demo.po.SysLoadFileInfo;
import com.example.demo.service.SysLoadFileInfoService;

/**
 * 文件名 ： LoadFile.java
 * 包 名 ： com.example.demo.run
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年7月28日 下午4:08:57
 * 版 本 ： V1.0
 */
@Component
@Order(2)
public class LoadFile implements ApplicationRunner {
	static Logger			logger	= LoggerFactory.getLogger(LoadFile.class);
	
	@Autowired
	UserConfig				userConfig;

	static String[]			names	= { ".", "..", "/" };

	@Autowired
	SysLoadFileInfoService	sysLoadFileInfoService;
	
	/**
	 * 方法名 ： run
	 * 功 能 ： 启动时处理文件信息录入表
	 * 参 数 ： @param args
	 * 参 数 ： @throws Exception
	 * 参 考 ： @see org.springframework.boot.ApplicationRunner#run(org.springframework.boot.ApplicationArguments)
	 * 作 者 ： Administrator
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info(">>_扫描_<<");
		if (userConfig.getReadfile() && StringUtils.isNotBlank(userConfig.getFileDir())) {
			logger.info(">>_扫描开始_//");
			File dir = new File(userConfig.getFileDir());
			if (!dir.exists()) {
				logger.error("<<_数据文件的路径不存在,请确认无误后再启动_&&");
				System.exit(0);
			} else if (dir.exists()) {
				toLoadFiles(dir);
			}
			
			logger.info(">>_扫描结束_//");
		} else {
			logger.info(">>_不需要扫描_//");
//			logger.error("<<_根本不会进入该方法_&&");
		}
	}
	
	/**
	 * 方法名： toLoadFiles
	 * 功 能： 读取该路径下的文件
	 * 参 数： @param dir
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	private void toLoadFiles(File dir) {
		File[] files = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					String name = file.getName();
					// 文件名进行特殊比较判断. ,.. 这些在读取文件时可能会被拉去到的隐藏特殊路径符号
					if (name == null || Arrays.asList(names).contains(name)) {
						return;
					}
					// 迭代读取该路径下的文件
					toLoadFiles(file);
				} else if (file.isFile()) {
					SysLoadFileInfo info = new SysLoadFileInfo(file.getAbsolutePath());
					QueryWrapper<SysLoadFileInfo> queryWrapper = new QueryWrapper<>(info);
					info = sysLoadFileInfoService.getOne(queryWrapper);
					if (info != null) {
						logger.info(">>_文件id:{},path:\"{}\"已存在，不需要写入库中。_//", info.getUuid(), info.getPath());
					} else {
						info = new SysLoadFileInfo(file.getAbsolutePath(), file.getName(), file.length());
						sysLoadFileInfoService.save(info);
						logger.info(">>_文件 path:\"{}\"已写入库中。_//", info.getPath());
					}
				}

			}
		}
	}
	
}
