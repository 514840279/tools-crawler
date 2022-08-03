package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.chuxue.application.common.base.MybatisBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dao.SysLoadFileInfoDao;
import com.example.demo.po.FileState;
import com.example.demo.po.FileType;
import com.example.demo.po.SysDbmsTabsColsInfo;
import com.example.demo.po.SysDbmsTabsTableInfo;
import com.example.demo.po.SysLoadFileColsInfo;
import com.example.demo.po.SysLoadFileInfo;
import com.example.demo.service.readFile.CsvFileRead;
import com.example.demo.service.readFile.DataFileRead;
import com.example.demo.service.readFile.JsonFileRead;
import com.example.demo.service.readFile.TxtFileRead;
import com.example.demo.service.readFile.XlsFileRead;
import com.example.demo.service.readFile.XlsxFileRead;
import com.example.demo.service.readFile.XmlFileRead;
import com.example.demo.vo.SysLoadFileInfoVo;

/**
 * @文件名 SysLoadFileInfoService.java
 * @包名 com.example.demo.service
 * @描述 service层
 * @时间 2022年07月28日 15:54:33
 * @author
 * @版本 V1.0
 */
@Service
public class SysLoadFileInfoService extends MybatisBaseServiceImpl<SysLoadFileInfoDao, SysLoadFileInfo> {
	
	@Autowired
	XlsFileRead					xlsFileRead;

	@Autowired
	XlsxFileRead				xlsxFileRead;

	@Autowired
	CsvFileRead					csvFileRead;

	@Autowired
	TxtFileRead					txtFileRead;

	@Autowired
	XmlFileRead					xmlFileRead;

	@Autowired
	JsonFileRead				jsonFileRead;

	@Autowired
	DataFileRead				dataFileRead;

	@Autowired
	SysLoadFileColsInfoService	sysLoadFileColsInfoService;

	@Autowired
	SysLoadFileInfoDao			sysLoadFileInfoDao;

	@Autowired
	SysDbmsTabsColsInfoService	sysDbmsTabsColsInfoService;

	@Autowired
	SysDbmsTabsTableInfoService	sysDbmsTabsTableInfoService;
	
	/**
	 * 方法名： readFile
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param vo
	 * 参 数： @return
	 * 返 回： SysLoadFileInfoVo
	 * 作 者 ： Administrator
	 * @throws
	 */
	public void readFile(SysLoadFileInfoVo vo) {
		SysLoadFileInfo info = vo.getInfo();
		switch (info.getFileType()) {
			case FileType.XLS:
				xlsFileRead.readFileXls(vo);
				break;
			case FileType.XLSX:
				xlsxFileRead.readFileXlsx(vo);
				break;
			case FileType.CSV:
				csvFileRead.readFileCsv(vo);
				break;
			case FileType.TXT:
				txtFileRead.readFileTxt(vo);
				break;
			case FileType.XML:
				xmlFileRead.readFileXml(vo);
				break;
			case FileType.JSON:
				jsonFileRead.readFileJson(vo);
				break;
			case FileType.DATA:
				dataFileRead.readFileData(vo);
				break;
			default:
				System.err.println("没有支持的类型");
				break;
		}
	}

	/**
	 * 方法名： saveFileConfig
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param vo
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	@Transactional
	public void saveFileConfig(SysLoadFileInfoVo vo) {
		SysLoadFileInfo info = vo.getInfo();
		SysLoadFileColsInfo cols = new SysLoadFileColsInfo();
		cols.setFileUuid(info.getUuid());
		QueryWrapper<SysLoadFileColsInfo> queryWrapper = new QueryWrapper<>(cols);
		List<SysLoadFileColsInfo> reList = sysLoadFileColsInfoService.list(queryWrapper);
		if (reList != null && reList.size() > 0) {
			sysLoadFileColsInfoService.removeBatchByIds(reList);
		}
		info.setFileState(FileState.CONFIG);
		saveOrUpdate(info);
		sysLoadFileColsInfoService.saveBatch(vo.getColumns());
	}

	/**
	 * 方法名： sqlText
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param vo
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	@Transactional
	public SysDbmsTabsTableInfo sqlText(SysLoadFileInfoVo vo) {
		sysLoadFileInfoDao.createTable(vo.getSqlText());
		String table = vo.getSqlText().split("\r\n")[0];
		String tableName = table.replaceAll("CREATE|TABLE|IF|NOT|EXISTS|\\(| ", "").trim();

		SysDbmsTabsTableInfo tab = new SysDbmsTabsTableInfo(vo.getInfo().getFileName(), tableName);
		List<SysDbmsTabsColsInfo> colsInfos = new ArrayList<>();
		for (SysLoadFileColsInfo fileColsInfo : vo.getColumns()) {
			SysDbmsTabsColsInfo tabsColsInfo = new SysDbmsTabsColsInfo(fileColsInfo.getColumnDesc(), fileColsInfo.getColumnName(), fileColsInfo.getColumnType(), tab.getUuid());
			colsInfos.add(tabsColsInfo);
		}

		sysDbmsTabsTableInfoService.save(tab);
		sysDbmsTabsColsInfoService.saveBatch(colsInfos);
		return tab;
	}
	
}
