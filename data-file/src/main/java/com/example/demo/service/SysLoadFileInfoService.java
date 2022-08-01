package com.example.demo.service;

import org.chuxue.application.common.base.MybatisBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.po.FileType;
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
public class SysLoadFileInfoService extends MybatisBaseServiceImpl<SysLoadFileInfo> {
	
	@Autowired
	XlsFileRead		xlsFileRead;

	@Autowired
	XlsxFileRead	xlsxFileRead;

	@Autowired
	CsvFileRead		csvFileRead;

	@Autowired
	TxtFileRead		txtFileRead;

	@Autowired
	XmlFileRead		xmlFileRead;

	@Autowired
	JsonFileRead	jsonFileRead;

	@Autowired
	DataFileRead	dataFileRead;
	
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
	
}
