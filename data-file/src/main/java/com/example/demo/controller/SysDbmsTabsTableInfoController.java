package com.example.demo.controller;

import org.chuxue.application.common.base.BaseController;
import org.chuxue.application.common.base.MybatisBaseConrollerImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.SysDbmsTabsTableInfoDao;
import com.example.demo.po.SysDbmsTabsTableInfo;

/**
 * @文件名 SysDbmsTabsTableInfoController.java
 * @包名 com.example.demo.controller
 * @描述 controller层
 * @时间 2022年08月01日 15:15:14
 * @author
 * @版本 V1.0
 */
@RestController
@RequestMapping("/sysDbmsTabsTableInfo")
public class SysDbmsTabsTableInfoController extends MybatisBaseConrollerImpl<SysDbmsTabsTableInfoDao, SysDbmsTabsTableInfo> implements BaseController<SysDbmsTabsTableInfo> {
	
}
