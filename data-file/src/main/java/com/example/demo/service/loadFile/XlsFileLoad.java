package com.example.demo.service.loadFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
 * 文件名 ： XlsFileRead.java
 * 包 名 ： com.example.demo.service.readFile
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年8月1日 下午5:36:50
 * 版 本 ： V1.0
 */
@Component
@Order(21)
public class XlsFileLoad {
	
	@Autowired
	UserConfig					userConfig;

	@Autowired
	SysLoadFileLogInfoService	sysLoadFileLogInfoService;

	@Autowired
	SqlBatchUtils				sqlBatchUtils;

	/**
	 * 方法名： readFileXls
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param vo
	 * 参 数： @return
	 * 返 回： SysLoadFileInfoVo
	 * 作 者 ： Administrator
	 * @throws
	 */
	public void loadFileXls(SysLoadFileColsMappingVo vo) {
		SysLoadFileInfo info = vo.getFileInfo();
		String path = info.getPath();
		File file = new File(path);
		if (file.exists()) {
			try {
				fileload(file, vo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			throw new BaseException(-1, "没有找到文件");
		}
	}
	
	/**
	 * @throws IOException
	 * 方法名： fileReadConfTopTen
	 * 功 能： 没有配置的直接读取前十条
	 * 参 数： @param file
	 * 参 数： @param vo
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	private void fileload(File file, SysLoadFileColsMappingVo vo) throws IOException {
		SysLoadFileLogInfo fileLog = vo.getFileLog();
		file.setReadable(true);// 设置可读
		
		// 提交行数限制，临时变量
		Long rowCommit = userConfig.getCommitRows();
		// 打开文件开始读取数据
		file.setReadable(true);// 设置可读
		// 保存数据用
		List<Map<String, String>> rowDataLists = null;
		// 文件字段信息
		Map<Integer, SysLoadFileColsInfo> fileColsInfos = vo.getFileColsInfos();
		// 读取Excel
		SysLoadFileInfo info = vo.getFileInfo();
		
		// 读取Excel
		try (FileInputStream modelfins = new FileInputStream(file); POIFSFileSystem modelfs = new POIFSFileSystem(modelfins); Workbook workbook = new HSSFWorkbook(modelfs);) {
			Sheet sheet = workbook.getSheet(info.getFileSheetName());

//			Integer rownum = sheet.getFirstRowNum();
			rowDataLists = new ArrayList<>();
			Row row = null;
			Cell cell = null;
			Long arow = fileLog.getRowCount();

			for (int intRow = arow.intValue(); intRow <= sheet.getLastRowNum(); intRow++) {
				row = sheet.getRow(intRow);
				fileLog.setRowCount((long) intRow);
				Map<String, String> adataList = new HashMap<>();
				for (int intCol = 0; intCol < fileColsInfos.size(); intCol++) {
					cell = row.getCell(intCol);
					SysLoadFileColsInfo colInfo = fileColsInfos.get(intCol);
					String tabColName = colInfo.getColumnDesc();
					if (cell != null) {
						// 不同数据类型处理
						CellType cellType = cell.getCellType();
						if (cellType == CellType.NUMERIC) {
							if (DateUtil.isCellDateFormatted(cell)) {
								short format = cell.getCellStyle().getDataFormat();
								String cellValue = "";
								SimpleDateFormat sdf = null;
								if (format == 20 || format == 32) {
									sdf = new SimpleDateFormat("HH:mm");
								} else if (format == 14 || format == 31 || format == 57 || format == 58 || format == 176) {
									// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
									sdf = new SimpleDateFormat("yyyy-MM-dd");
								} else {// 日期
									sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								}
								try {
									cellValue = sdf.format(cell.getDateCellValue());// 日期
									adataList.put(tabColName, cellValue);
								} catch (Exception e) {
									try {
										throw new Exception("exception on get date data !".concat(e.toString()));
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								} finally {
									sdf = null;
								}
							} else {
								adataList.put(tabColName, Double.toString(cell.getNumericCellValue()));
							}
						} else if (cellType == CellType.STRING) {
							RichTextString s = cell.getRichStringCellValue();
							if (s != null && !"null".equals(s)) {
								adataList.put(tabColName, s.getString());
							} else {
								adataList.put(tabColName, null);
							}
						} else if (cellType == CellType.BLANK) {
							adataList.put(tabColName, null);
						} else if (cellType == CellType.BOOLEAN) {
							adataList.put(tabColName, Boolean.toString(cell.getBooleanCellValue()));
						} else if (cellType == CellType.ERROR) {
							adataList.put(tabColName, Integer.valueOf(cell.getErrorCellValue()).toString());
						} else if (cellType == CellType.FORMULA) {
							if (cell.getCachedFormulaResultType().equals(CellType.FORMULA)) {
								Double d = cell.getNumericCellValue();
								adataList.put(tabColName, d.toString());
							} else if (cell.getCachedFormulaResultType().equals(CellType.STRING)) {
								String v = cell.getRichStringCellValue().toString();
								adataList.put(tabColName, v.toString());
							}
							
						} else { // nothing29
						}
					} else {
						adataList.put(tabColName, null);
					}
				}
				rowDataLists.add(adataList);
				// 每够一千限制就去处理入库
				if (rowDataLists.size() == rowCommit) {
					writeToDb(vo, rowCommit, rowDataLists);
					rowDataLists = new ArrayList<>();
				}
			}
			// 剩余数据入库
			if (rowDataLists.size() > 0) {
				writeToDb(vo, rowCommit, rowDataLists);
			}
		} catch (

		Exception e) {
			// TODO: handle exception
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
