package com.example.demo.controller;

import org.chuxue.application.common.base.BaseController;
import org.chuxue.application.common.base.MybatisBaseConrollerImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.SysLoadFileLogInfoDao;
import com.example.demo.po.SysLoadFileLogInfo;

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

}
