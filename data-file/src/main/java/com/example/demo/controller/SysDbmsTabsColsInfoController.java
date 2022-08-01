package com.example.demo.controller;

import org.chuxue.application.common.base.BaseController;
import org.chuxue.application.common.base.MybatisBaseConrollerImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.po.SysDbmsTabsColsInfo;

/**
 * @文件名 SysDbmsTabsColsInfo`Controller.java
 * @包名 com.example.demo.controller
 * @描述 controller层
 * @时间 2022年08月01日 15:15:10
 * @author
 * @版本 V1.0
 */
@RestController
@RequestMapping("/sysDbmsTabsColsInfo")
public class SysDbmsTabsColsInfoController extends MybatisBaseConrollerImpl<SysDbmsTabsColsInfo> implements BaseController<SysDbmsTabsColsInfo> {

}
