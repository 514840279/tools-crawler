package com.example.demo.service;

import org.chuxue.application.common.base.MybatisBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dao.SysLoadFileColsMappingDao;
import com.example.demo.po.FileState;
import com.example.demo.po.RunState;
import com.example.demo.po.SysLoadFileColsMapping;
import com.example.demo.po.SysLoadFileInfo;
import com.example.demo.po.SysLoadFileLogInfo;
import com.example.demo.vo.SysLoadFileColsMappingVo;

/**
 * @文件名 SysLoadFileColsMappingService.java
 * @包名 com.example.demo.service
 * @描述 service层
 * @时间 2022年07月28日 15:54:33
 * @author
 * @版本 V1.0
 */
@Service
public class SysLoadFileColsMappingService extends MybatisBaseServiceImpl<SysLoadFileColsMappingDao, SysLoadFileColsMapping> {
	
	@Autowired
	SysLoadFileInfoService		sysLoadFileInfoService;
	
	@Autowired
	SysLoadFileLogInfoService	sysLoadFileLogInfoService;
	
	/**
	 * 方法名： saveFileMappingConfig
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param vo
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	@Transactional
	public void saveFileMappingConfig(SysLoadFileColsMappingVo vo) {
		SysLoadFileInfo info = vo.getFileInfo();
		SysLoadFileColsMapping mapping = new SysLoadFileColsMapping();

		mapping.setFileUuid(info.getUuid());
		QueryWrapper<SysLoadFileColsMapping> queryWrapper = new QueryWrapper<>(mapping);
//		List<SysLoadFileColsMapping> re = list(queryWrapper);
		remove(queryWrapper);
		saveBatch(vo.getMappings());
		info.setFileMappingState(FileState.CONFIG);
		sysLoadFileInfoService.saveOrUpdate(info);
		
		SysLoadFileLogInfo log = new SysLoadFileLogInfo(info.getUuid());
		QueryWrapper<SysLoadFileLogInfo> logWrapper = new QueryWrapper<>(log);
		logWrapper.orderByDesc("create_time");
		logWrapper.last(" limit 1");
		log = sysLoadFileLogInfoService.getOne(logWrapper);
		if (log == null) {
			log = new SysLoadFileLogInfo(info.getUuid(), 0L, 0L, null, RunState.STRAT);
			sysLoadFileLogInfoService.save(log);
		}
	}

}
