package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.chuxue.application.common.base.MybatisBaseDao;

import com.example.demo.po.SysLoadFileInfo;

/**
 * @文件名 SysLoadFileInfoDao.java
 * @包名 com.example.demo.dao
 * @描述 dao层
 * @时间 2022年07月28日 15:54:33
 * @author
 * @版本 V1.0
 */
@Mapper
public interface SysLoadFileInfoDao extends MybatisBaseDao<SysLoadFileInfo> {
	
}
