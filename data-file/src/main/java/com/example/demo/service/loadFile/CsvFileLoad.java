package com.example.demo.service.loadFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.chuxue.application.common.base.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.config.UserConfig;
import com.example.demo.po.RunState;
import com.example.demo.po.SysLoadFileColsInfo;
import com.example.demo.po.SysLoadFileInfo;
import com.example.demo.po.SysLoadFileLogInfo;
import com.example.demo.service.SysLoadFileLogInfoService;
import com.example.demo.vo.SysLoadFileColsMappingVo;

/**
 * 文件名 ： CsvFileRead.java
 * 包 名 ： com.example.demo.service.readFile
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年8月1日 下午5:39:39
 * 版 本 ： V1.0
 */
@Component
@Order(23)
public class CsvFileLoad {

	@Autowired
	UserConfig					userConfig;
	
	@Autowired
	SysLoadFileLogInfoService	sysLoadFileLogInfoService;
	
	@Autowired
	SqlBatchUtils				sqlBatchUtils;

	/**
	 * 方法名： readFileCsv
	 * 功 能： 读取文件
	 * 参 数： @param vo
	 * 参 数： @return
	 * 返 回： SysLoadFileInfoVo
	 * 作 者 ： Administrator
	 * @throws
	 */
	public void loadFileCsv(SysLoadFileColsMappingVo vo) {
		SysLoadFileInfo info = vo.getFileInfo();
		String path = info.getPath();
		File file = new File(path);
		if (file.exists()) {
			fileLoad(file, vo);
		} else {
			throw new BaseException(-1, "没有找到文件");
		}
	}

	/**
	 * 方法名： fileReadConfTopTen
	 * 功 能： 没有配置的直接读取前十条
	 * 参 数： @param file
	 * 参 数： @param vo
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	private void fileLoad(File file, SysLoadFileColsMappingVo vo) {
		SysLoadFileLogInfo fileLog = vo.getFileLog();
		// 提交行数限制，临时变量
		Long rowCommit = userConfig.getCommitRows();
		
		file.setReadable(true);// 设置可读
		// 保存数据用
		List<Map<String, String>> rowDataLists = null;
		// 文件字段信息
		Map<Integer, SysLoadFileColsInfo> fileColsInfos = vo.getFileColsInfos();

		BufferedReader br = null;
		String line = "";
		// 读取Excel
		SysLoadFileInfo info = vo.getFileInfo();
		
		try {
			br = new BufferedReader(new FileReader(file));
			Long arow = fileLog.getRowCount();
			rowDataLists = new ArrayList<>();
			String tLine = "";
			
			Long crows = 0L;
			// 读取到的内容给line变量 只读十条 ，跳过前面
			while ((line = br.readLine()) != null) {
				crows += 1;
				if (crows > arow) {
					fileLog.setRowCount(crows);
					// 数据对象
					Map<String, String> adataList = new HashMap<>();
					// Enclosed
					String[] data_arr = line.split(info.getSeparator());

					if (data_arr.length >= fileColsInfos.size()) {
						for (int i = 0; i < fileColsInfos.size(); i++) {
							String string = data_arr[i].replace(info.getEnclosed(), "");
							SysLoadFileColsInfo colInfo = fileColsInfos.get(i);
							String tabColName = colInfo.getColumnDesc();
							adataList.put(tabColName, string);
						}
						rowDataLists.add(adataList);
						// 每够一千限制就去处理入库
						if (rowDataLists.size() == rowCommit) {
							writeToDb(vo, rowCommit, rowDataLists);
							rowDataLists = new ArrayList<>();
						}
					} else {
						tLine += line;

						// 数据对象
						// Enclosed
						data_arr = tLine.split(info.getSeparator());
						if (data_arr.length >= fileColsInfos.size()) {
							for (int i = 0; i < fileColsInfos.size(); i++) {
								String string = data_arr[i].replace(info.getEnclosed(), "");
								SysLoadFileColsInfo colInfo = fileColsInfos.get(i);
								String tabColName = colInfo.getColumnDesc();
								adataList.put(tabColName, string);
							}
							rowDataLists.add(adataList);
							// 每够一千限制就去处理入库
							if (rowDataLists.size() == rowCommit) {
								writeToDb(vo, rowCommit, rowDataLists);
								rowDataLists = new ArrayList<>();
							}
							tLine = "";
						}
						// TODO 记录错误的信息到文件中
						System.out.println("error-line：" + line);
					}
				}
			}
			// 剩余数据入库
			if (rowDataLists.size() > 0) {
				writeToDb(vo, rowCommit, rowDataLists);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeToDb(SysLoadFileColsMappingVo vo, Long rowCommit, List<Map<String, String>> rowDataLists) throws IOException {
		// 5. 创建临时变量 统计读取行数，错误信息、提交的行数，临时行数
		SysLoadFileLogInfo fileLog = vo.getFileLog();

		// 正确提交的数据量
		Long commitCount = fileLog.getComplateRows() == null ? 0L : fileLog.getComplateRows();
		// 提交失败数据量
		Long erroCont = fileLog.getErrorRows() == null ? 0L : fileLog.getErrorRows();
		
		// 错误文件路径
		String errorPath = fileLog.getErrorFile() == null ? userConfig.getErrorDir() + System.currentTimeMillis() + ".txt" : fileLog.getErrorFile();
		File errorFile = new File(errorPath);

		// 去入库
		List<Map<String, String>> error = insertData(rowDataLists, vo, rowCommit);
		// 处理错误信息
		if (error != null && error.size() > 0) {
			// 错误文件对象
			erroCont = erroCont + error.size();
			commitCount += rowDataLists.size() - error.size();
			fileLog.setErrorRows(erroCont);
			fileLog.setComplateRows(commitCount);
			fileLog.setErrorFile(errorPath);
			if (!errorFile.exists()) {
				errorFile.createNewFile();
			}
			// TODO 记录错误的信息到文件中

		} else {
			commitCount += rowDataLists.size();
			fileLog.setComplateRows(commitCount);
		}
		fileLog.setRunState(RunState.RUNNING);
		fileLog.setUpdateTime(new Date());
		// 修改入库信息
		sysLoadFileLogInfoService.saveOrUpdate(fileLog);
		
	}
	
	/**
	 * @param rowCommit
	 * 方法名： insertData
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param rowDataLists
	 * 参 数： @param vo
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	private List<Map<String, String>> insertData(List<Map<String, String>> rowDataLists, SysLoadFileColsMappingVo vo, Long rowCommit) {
		// 临时存储对象
		List<Map<String, String>> errorDataList = new ArrayList<>();
		try {
			// commit;
			sqlBatchUtils.batchInsert(vo.getSqlstr(), rowDataLists, vo.getMappings());
		} catch (Exception e) {
			List<Map<String, String>> tcomplaceDataList = new ArrayList<>();
			for (Map<String, String> map : rowDataLists) {
				tcomplaceDataList.add(map);
				// 处理入库语句
				if (tcomplaceDataList.size() == rowCommit / 10) {
					if (rowCommit == 1) {
						// 处理错误信息
						errorDataList.add(map);
					}
					errorDataList.addAll(insertData(tcomplaceDataList, vo, rowCommit / 10));
					tcomplaceDataList = new ArrayList<>();
				}
			}
			// 处理入库语句
			if (tcomplaceDataList.size() > 0) {
				try {
					// commit;
					sqlBatchUtils.batchInsert(vo.getSqlstr(), tcomplaceDataList, vo.getMappings());
					tcomplaceDataList = new ArrayList<>();
				} catch (Exception e1) {
					if (rowCommit == 1) {
						// 处理错误信息
						errorDataList.add(tcomplaceDataList.get(0));
					}
					errorDataList.addAll(insertData(tcomplaceDataList, vo, rowCommit / 10));
				}
			}
		}
		
		return errorDataList;
	}
}
