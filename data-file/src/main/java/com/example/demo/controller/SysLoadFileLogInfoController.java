package com.example.demo.controller;

import org.chuxue.application.common.base.BaseController;
import org.chuxue.application.common.base.BaseResult;
import org.chuxue.application.common.base.MybatisBaseConrollerImpl;
import org.chuxue.application.common.base.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.SysLoadFileLogInfoDao;
import com.example.demo.po.SysLoadFileInfo;
import com.example.demo.po.SysLoadFileLogInfo;
import com.example.demo.service.SysLoadFileLogInfoService;

/**
 * @文件名 SysLoadFileLogInfoController.java
 * @包名 com.example.demo.controller
 * @描述 controller层
 * @时间 2022年07月28日 15:54:33
 * @author
 * @版本 V1.0
 */
@RestController
@RequestMapping("/sysLoadFileLogInfo")
public class SysLoadFileLogInfoController extends MybatisBaseConrollerImpl<SysLoadFileLogInfoDao, SysLoadFileLogInfo> implements BaseController<SysLoadFileLogInfo> {
	static Logger				logger	= LoggerFactory.getLogger(SysLoadFileLogInfoController.class);
	@Autowired
	SysLoadFileLogInfoService	sysLoadFileLogInfoService;
	
	@RequestMapping(value = "/reStartJob", method = RequestMethod.POST)
	public BaseResult<String> reStartJob(@RequestBody SysLoadFileInfo info) {
		logger.info("<reStartJob> param info:{} ", info.toString());
		try {
			// 简单分页查询
			String str = sysLoadFileLogInfoService.reStartJob(info);
			return ResultUtil.success(str);
		} catch (Exception e) {
			logger.error("<reStartJob> error:{} ", e.getMessage());
			return ResultUtil.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/stopJob", method = RequestMethod.POST)
	public BaseResult<String> stopJob(@RequestBody SysLoadFileInfo info) {
		logger.info("<stopJob> param info:{} ", info.toString());
		try {
			// 简单分页查询
			String str = sysLoadFileLogInfoService.stopJob(info);
			return ResultUtil.success(str);
		} catch (Exception e) {
			logger.error("<stopJob> error:{} ", e.getMessage());
			return ResultUtil.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/continueJob", method = RequestMethod.POST)
	public BaseResult<String> continueJob(@RequestBody SysLoadFileInfo info) {
		logger.info("<continueJob> param info:{} ", info.toString());
		try {
			// 简单分页查询
			String str = sysLoadFileLogInfoService.continueJob(info);
			return ResultUtil.success(str);
		} catch (Exception e) {
			logger.error("<continueJob> error:{} ", e.getMessage());
			return ResultUtil.error(e.getMessage());
		}
	}

}
