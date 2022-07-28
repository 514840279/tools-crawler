package com.example.demo.controller;

import org.chuxue.application.common.base.BaseController;
import org.chuxue.application.common.base.MybatisBaseConrollerImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.po.SysLoadFileInfo;

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
public class SysLoadFileInfoController extends MybatisBaseConrollerImpl<SysLoadFileInfo> implements BaseController<SysLoadFileInfo> {
	
}
