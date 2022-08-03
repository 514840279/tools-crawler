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

import com.example.demo.dao.SysLoadFileInfoDao;
import com.example.demo.po.SysDbmsTabsTableInfo;
import com.example.demo.po.SysLoadFileInfo;
import com.example.demo.service.SysLoadFileInfoService;
import com.example.demo.vo.SysLoadFileInfoVo;

/**
 * @文件名 SysLoadFileInfoController.java
 * @包名 com.example.demo.controller
 * @描述 controller层
 * @时间 2022年07月28日 15:54:33
 * @author
 * @版本 V1.0
 */
@RestController
@RequestMapping("/sysLoadFileInfo")
public class SysLoadFileInfoController extends MybatisBaseConrollerImpl<SysLoadFileInfoDao, SysLoadFileInfo> implements BaseController<SysLoadFileInfo> {
	
	static Logger			logger	= LoggerFactory.getLogger(SysLoadFileInfoController.class);
	
	@Autowired
	SysLoadFileInfoService	sysLoadFileInfoService;
	
	@RequestMapping(value = "/readFile", method = RequestMethod.POST)
	public BaseResult<SysLoadFileInfoVo> readFile(@RequestBody SysLoadFileInfoVo vo) {
		logger.info("<readFile> param info:{} ", vo.toString());
		try {
			// 简单分页查询
			sysLoadFileInfoService.readFile(vo);
			return ResultUtil.success(vo);
		} catch (Exception e) {
			logger.error("<readFile> error:{} ", e.getMessage());
			return ResultUtil.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/saveFileConfig", method = RequestMethod.POST)
	public BaseResult<String> saveFileConfig(@RequestBody SysLoadFileInfoVo vo) {
		logger.info("<saveFileConfig> param info:{} ", vo.toString());
		try {
			// 简单分页查询
			sysLoadFileInfoService.saveFileConfig(vo);
			return ResultUtil.success("配置信息修改成功。");
		} catch (Exception e) {
			logger.error("<saveFileConfig> error:{} ", e.getMessage());
			return ResultUtil.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/sqlText", method = RequestMethod.POST)
	public BaseResult<SysDbmsTabsTableInfo> sqlText(@RequestBody SysLoadFileInfoVo vo) {
		logger.info("<sqlText> param info:{} ", vo.toString());
		try {
			// 简单分页查询
			SysDbmsTabsTableInfo tab = sysLoadFileInfoService.sqlText(vo);
			
			return ResultUtil.success(tab);
		} catch (Exception e) {
			logger.error("<sqlText> error:{} ", e.getMessage());
			return ResultUtil.error(e.getMessage());
		}
	}

}
