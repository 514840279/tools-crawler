package com.example.demo.service.loadFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.chuxue.application.common.base.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.po.FileType;
import com.example.demo.po.RunState;
import com.example.demo.po.SysDbmsTabsColsInfo;
import com.example.demo.po.SysDbmsTabsTableInfo;
import com.example.demo.po.SysLoadFileColsInfo;
import com.example.demo.po.SysLoadFileColsMapping;
import com.example.demo.po.SysLoadFileInfo;
import com.example.demo.po.SysLoadFileLogInfo;
import com.example.demo.service.SysDbmsTabsColsInfoService;
import com.example.demo.service.SysDbmsTabsTableInfoService;
import com.example.demo.service.SysLoadFileColsInfoService;
import com.example.demo.service.SysLoadFileColsMappingService;
import com.example.demo.service.SysLoadFileInfoService;
import com.example.demo.service.SysLoadFileLogInfoService;
import com.example.demo.vo.SysLoadFileColsMappingVo;

/**
 * 文件名 ： AsyncLoadFileDataService.java
 * 包 名 ： com.example.demo.service.loadFile
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年8月5日 下午2:40:55
 * 版 本 ： V1.0
 */
@Service
public class AsyncLoadFileDataService {
	private static final Logger		logger	= LoggerFactory.getLogger(AsyncLoadFileDataService.class);
	
	@Autowired
	XlsFileLoad						xlsFileLoad;

	@Autowired
	XlsxFileLoad					xlsxFileLoad;

	@Autowired
	CsvFileLoad						csvFileLoad;

	@Autowired
	TxtFileLoad						txtFileLoad;

	@Autowired
	XmlFileLoad						xmlFileLoad;

	@Autowired
	JsonFileLoad					jsonFileLoad;

	@Autowired
	DataFileLoad					dataFileLoad;
	
	@Autowired
	SysLoadFileInfoService			sysLoadFileInfoService;

	@Autowired
	SysLoadFileColsInfoService		sysLoadFileColsInfoService;

	@Autowired
	SysDbmsTabsTableInfoService		sysDbmsTabsTableInfoService;
	
	@Autowired
	SysDbmsTabsColsInfoService		sysDbmsTabsColsInfoService;
	
	@Autowired
	SysLoadFileLogInfoService		sysLoadFileLogInfoService;

	@Autowired
	SysLoadFileColsMappingService	sysLoadFileColsMappingService;

	// 报告多线程调用
	@Async("asyncServiceExecutor")
	public CompletableFuture<String> executeAsync(SysLoadFileLogInfo log) {
		logger.info("start executeAsync");
		try {
			// 1.获取配置数据
			SysLoadFileColsMappingVo mappingVo = mappingConfig(log);
			loadFile(mappingVo);
		} catch (Exception e) {
			log.setErrorMessage(e.getMessage());
			log.setRunState(RunState.ERROR);
			sysLoadFileLogInfoService.saveOrUpdate(log);
			return CompletableFuture.completedFuture(RunState.ERROR);
		}
		
		log.setRunState(RunState.END);
		sysLoadFileLogInfoService.saveOrUpdate(log);
		logger.info("end executeAsync");
		return CompletableFuture.completedFuture(RunState.END);
	}

	/**
	 * 方法名： mappingConfig
	 * 功 能： 1. 读取配置信息
	 * 参 数： @param log
	 * 参 数： @return
	 * 返 回： SysLoadFileColsMappingVo
	 * 作 者 ： Administrator
	 * @throws
	 */
	private SysLoadFileColsMappingVo mappingConfig(SysLoadFileLogInfo log) {
		SysLoadFileInfo fileInfo = sysLoadFileInfoService.getById(log.getFileUuid());
		QueryWrapper<SysLoadFileColsInfo> colsInfoWrapper = new QueryWrapper<>();
		colsInfoWrapper.in("file_uuid", log.getFileUuid());
		List<SysLoadFileColsInfo> fileColsInfos = sysLoadFileColsInfoService.list(colsInfoWrapper);
		QueryWrapper<SysLoadFileColsMapping> mappingWrapper = new QueryWrapper<>();
		mappingWrapper.in("file_uuid", log.getFileUuid());
		List<SysLoadFileColsMapping> mappings = sysLoadFileColsMappingService.list(mappingWrapper);
		QueryWrapper<SysDbmsTabsTableInfo> tableWrapper = new QueryWrapper<>();
		tableWrapper.in("uuid", mappings.get(0).getTabsUuid());
		SysDbmsTabsTableInfo tableInfo = sysDbmsTabsTableInfoService.getOne(tableWrapper);
		
		QueryWrapper<SysDbmsTabsColsInfo> tableColWrapper = new QueryWrapper<>();
		tableColWrapper.in("table_uuid", mappings.get(0).getTabsUuid());
		List<SysDbmsTabsColsInfo> tableColInfo = sysDbmsTabsColsInfoService.list(tableColWrapper);

		return new SysLoadFileColsMappingVo(log, fileInfo, fileColsInfos, tableInfo, mappings, tableColInfo);
	}

	public void loadFile(SysLoadFileColsMappingVo vo) {
		SysLoadFileInfo info = vo.getFileInfo();
		try {
			switch (info.getFileType()) {
				case FileType.XLS:
					xlsFileLoad.loadFileXls(vo);
					break;
				case FileType.XLSX:
					xlsxFileLoad.loadFileXlsx(vo);
					break;
				case FileType.CSV:
					csvFileLoad.loadFileCsv(vo);
					break;
				case FileType.TXT:
					txtFileLoad.loadFileTxt(vo);
					break;
				case FileType.XML:
					xmlFileLoad.loadFileXml(vo);
					break;
				case FileType.JSON:
					jsonFileLoad.loadFileJson(vo);
					break;
				case FileType.DATA:
					dataFileLoad.loadFileData(vo);
					break;
				default:
					throw new BaseException("没有支持的类型");
			}
		} catch (Exception e) {
			throw new BaseException(e.getMessage());
		}
	}
}
