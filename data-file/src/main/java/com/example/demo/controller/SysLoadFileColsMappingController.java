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

import com.example.demo.dao.SysLoadFileColsMappingDao;
import com.example.demo.po.SysLoadFileColsMapping;
import com.example.demo.service.SysLoadFileColsMappingService;
import com.example.demo.vo.SysLoadFileColsMappingVo;

/**
 * @文件名 SysLoadFileColsMappingController.java
 * @包名 com.example.demo.controller
 * @描述 controller层
 * @时间 2022年07月28日 15:54:33
 * @author
 * @版本 V1.0
 */
@RestController
@RequestMapping("/sysLoadFileColsMapping")
public class SysLoadFileColsMappingController extends MybatisBaseConrollerImpl<SysLoadFileColsMappingDao, SysLoadFileColsMapping> implements BaseController<SysLoadFileColsMapping> {

	static Logger					logger	= LoggerFactory.getLogger(SysLoadFileColsMappingController.class);

	@Autowired
	SysLoadFileColsMappingService	sysLoadFileColsMappingService;

	@RequestMapping(value = "/saveFileMappingConfig", method = RequestMethod.POST)
	public BaseResult<String> saveFileMappingConfig(@RequestBody SysLoadFileColsMappingVo vo) {
		logger.info("<saveFileConfig> param info:{} ", vo.toString());
		try {
			// 简单分页查询
			sysLoadFileColsMappingService.saveFileMappingConfig(vo);
			return ResultUtil.success("配置信息修改成功。");
		} catch (Exception e) {
			logger.error("<saveFileMappingConfig> error:{} ", e.getMessage());
			return ResultUtil.error(e.getMessage());
		}
	}

}
