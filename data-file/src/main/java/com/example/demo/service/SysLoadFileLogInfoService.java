package com.example.demo.service;

import java.util.Date;

import org.chuxue.application.common.base.MybatisBaseServiceImpl;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dao.SysLoadFileLogInfoDao;
import com.example.demo.po.RunState;
import com.example.demo.po.SysLoadFileInfo;
import com.example.demo.po.SysLoadFileLogInfo;

/**
 * @文件名 SysLoadFileLogInfoService.java
 * @包名 com.example.demo.service
 * @描述 service层
 * @时间 2022年07月28日 15:54:33
 * @author
 * @版本 V1.0
 */
@Service
public class SysLoadFileLogInfoService extends MybatisBaseServiceImpl<SysLoadFileLogInfoDao, SysLoadFileLogInfo> {

	// 查询最新的一条记录 如果没有则新建然后返回
	public SysLoadFileLogInfo queryTop(String fileUuid) {
		SysLoadFileLogInfo log = new SysLoadFileLogInfo(fileUuid);
		QueryWrapper<SysLoadFileLogInfo> logWrapper = new QueryWrapper<>(log);
		logWrapper.orderByDesc("create_time");
		logWrapper.last("limit 1");
		log = getOne(logWrapper);
		if (log == null) {
			log = new SysLoadFileLogInfo(fileUuid, 0L, 0L, null, RunState.STRAT);
			save(log);
		}
		return log;
	}

	/**
	 * 方法名： reStartJob
	 * 功 能： 创建新任务关闭老任务
	 * 参 数： @param vo
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	public String reStartJob(SysLoadFileInfo info) {
		String fileUuid = info.getUuid();
		SysLoadFileLogInfo log = queryTop(fileUuid);

		// 运行中、停止的需要关闭
		if (RunState.RUNNING.equals(log.getRunState()) || RunState.STOPED.equals(log.getRunState())) {
			log.setRunState(RunState.CLOSED);
			log.setUpdateTime(new Date());
			saveOrUpdate(log);
		}
		// 除准备态外其他的需要新建
		if (!RunState.STRAT.equals(log.getRunState())) {
			log = new SysLoadFileLogInfo(fileUuid, 0L, 0L, null, RunState.STRAT);
			save(log);
			return "修改配置信息成功！";
		} else {
			return "已经是准备状态，没必要重启！";
		}
		
	}

	/**
	 * 方法名： stopJob
	 * 功 能： 停止任务
	 * 参 数： @param vo
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	public String stopJob(SysLoadFileInfo info) {
		String fileUuid = info.getUuid();
		SysLoadFileLogInfo log = queryTop(fileUuid);
		if (RunState.RUNNING.equals(log.getRunState()) || RunState.STRAT.equals(log.getRunState())) {
			log.setRunState(RunState.STOPED);
			log.setUpdateTime(new Date());
			saveOrUpdate(log);
			
			return "修改配置信息成功！";
		} else {
			return "没有运行的记录，不需要停止！";
		}
		
	}
	
	/**
	 * 方法名： continueJob
	 * 功 能： 继续任务
	 * 参 数： @param vo
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	public String continueJob(SysLoadFileInfo info) {
		String fileUuid = info.getUuid();
		SysLoadFileLogInfo log = queryTop(fileUuid);
		if (RunState.STOPED.equals(log.getRunState())) {
			log.setRunState(RunState.STRAT);
			log.setUpdateTime(new Date());
			saveOrUpdate(log);
			return "修改配置信息成功！";
		} else {
			return "没有停止的记录，不需要继续！";
		}
		
	}

}
