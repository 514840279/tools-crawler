package com.example.demo.service.readFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.chuxue.application.common.base.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.config.UserConfig;
import com.example.demo.po.FileState;
import com.example.demo.po.SysLoadFileColsInfo;
import com.example.demo.po.SysLoadFileInfo;
import com.example.demo.vo.SysLoadFileInfoVo;

/**
 * 文件名 ： XlsxFileRead.java
 * 包 名 ： com.example.demo.service.readFile
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年8月1日 下午5:38:49
 * 版 本 ： V1.0
 */
@Component
@Order(12)
public class XlsxFileRead {

	@Autowired
	UserConfig userConfig;
	
	/**
	 * 方法名： readFileXlsx
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param vo
	 * 参 数： @return
	 * 返 回： SysLoadFileInfoVo
	 * 作 者 ： Administrator
	 * @throws
	 */
	public void readFileXlsx(SysLoadFileInfoVo vo) {
		SysLoadFileInfo info = vo.getInfo();
		String path = info.getPath();
		File file = new File(path);
		if (file.exists()) {
			try {
				fileReadConfTopTen(file, vo);
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
	private void fileReadConfTopTen(File file, SysLoadFileInfoVo vo) throws IOException {
		List<List<String>> rows = readXlsxFileTopData(file, vo);
		if (vo.getColumns() == null || vo.getColumns().size() == 0) {
			columnsSet(rows, vo);
		}
		rowsToDatas(rows, vo, vo.getColumns());
		vo.getInfo().setFileState(FileState.CONFIG);
	}
	
	/**
	 * 方法名： rowsToDatas
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param rows
	 * 参 数： @param vo
	 * 参 数： @param columns
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	private void rowsToDatas(List<List<String>> rows, SysLoadFileInfoVo vo, List<SysLoadFileColsInfo> columns) {
		// datas
		List<Map<String, String>> datas = new ArrayList<>();
		for (int i = 0; i < rows.size(); i++) {
			List<String> row = rows.get(i);

			if (i == 0 && "Y".equals(vo.getInfo().getHasHead())) {
				continue;
			}
			Map<String, String> dataMap = new HashMap<>();
			for (int j = 0; j < row.size(); j++) {
				String string = row.get(j);

//				string = string.replace(vo.getInfo().getEnclosed(), "");
				if ("Y".equals(vo.getInfo().getHasHead())) {
					try {
						SysLoadFileColsInfo colsInfo = columns.get(j);
						if (colsInfo != null) {
							dataMap.put(columns.get(j).getColumnName(), string);
						} else {
							dataMap.put("A_" + j, string);
						}
					} catch (Exception e) {
						SysLoadFileColsInfo colsInfo = new SysLoadFileColsInfo();
						colsInfo.setUuid(UUID.randomUUID().toString());
						colsInfo.setColumnName("A_" + j);
						colsInfo.setColumnDesc("A_" + j);
						colsInfo.setColumnLength(string.length() * 5);
						colsInfo.setColumnType("text");
						colsInfo.setDeleteFlag(0);
						colsInfo.setSort(j);
						colsInfo.setFileUuid(vo.getInfo().getUuid());
						columns.add(colsInfo);
						dataMap.put("A_" + j, string);
					}
				} else {
					dataMap.put("A_" + j, string);
				}
			}
			datas.add(dataMap);
		}
		vo.setDatas(datas);

	}
	
	/**
	 * 方法名： columnsSet
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param rows
	 * 参 数： @param vo
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	private void columnsSet(List<List<String>> rows, SysLoadFileInfoVo vo) {
		List<String> frow_arr = rows.get(0);
		// columns
		List<SysLoadFileColsInfo> columns = new ArrayList<>();
		int a = 0;
		for (int i = 0; i < frow_arr.size(); i++) {
			String key = frow_arr.get(i);

			SysLoadFileColsInfo colsInfo = new SysLoadFileColsInfo();
			colsInfo.setUuid(UUID.randomUUID().toString());
			if ("Y".equals(vo.getInfo().getHasHead()) && key != null) {
				colsInfo.setColumnName(key);
				colsInfo.setColumnDesc(key);
				colsInfo.setColumnLength(key.length() * 5);
			} else {
				colsInfo.setColumnName("A_" + i);
				colsInfo.setColumnDesc("A_" + i);
			}
			colsInfo.setColumnType("text");
			colsInfo.setDeleteFlag(0);
			colsInfo.setSort(a++);
			colsInfo.setFileUuid(vo.getInfo().getUuid());
			columns.add(colsInfo);
		}
		vo.setColumns(columns);

	}
	
	/**
	 * @throws IOException
	 * @param sysLoadFileInfo
	 * 方法名： readCsvFileTopData
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param file
	 * 参 数： @return
	 * 返 回： List<String>
	 * 作 者 ： Administrator
	 * @throws
	 */
	private List<List<String>> readXlsxFileTopData(File file, SysLoadFileInfoVo vo) throws IOException {
		file.setReadable(true);// 设置可读
		
		List<List<String>> allString = null;
		// 读取Excel
		try (FileInputStream modelfins = new FileInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(modelfins);) {
//			Iterator<Sheet> iteSheet = workbook.sheetIterator();
			List<SysLoadFileInfo> sheets = new ArrayList<>();
			for (Sheet sheet : workbook) {
				SysLoadFileInfo in = new SysLoadFileInfo();
				in.setFileSheetName(sheet.getSheetName());
				Integer ind = workbook.getSheetIndex(sheet);
				in.setFileSheetIndex(ind);
				sheets.add(in);
			}
			vo.getInfo().setFileSheetName(sheets.get(0).getFileSheetName());
			vo.getInfo().setFileSheetIndex(sheets.get(0).getFileSheetIndex());
			Sheet sheet = workbook.getSheet(vo.getInfo().getFileSheetName());
			
			Integer rownum = sheet.getFirstRowNum();
			allString = new ArrayList<>();
			Row row = null;
			Cell cell = null;
			int arow = vo.getInfo().getSkip();
			
			for (int intRow = rownum; intRow <= sheet.getLastRowNum() && allString.size() < userConfig.getReadRows(); intRow++) {
				if (arow <= intRow) {
					row = sheet.getRow(intRow);
					List<String> adataList = new ArrayList<>();
					for (int intCol = 0; intCol < row.getLastCellNum(); intCol++) {
						cell = row.getCell(intCol);
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
										adataList.add(cellValue);
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
									adataList.add(Double.toString(cell.getNumericCellValue()));
								}
							} else if (cellType == CellType.STRING) {
								RichTextString s = cell.getRichStringCellValue();
								if (s != null) {
									adataList.add(s.getString());
								} else {
									adataList.add(null);
								}
							} else if (cellType == CellType.BLANK) {
								adataList.add(null);
							} else if (cellType == CellType.BOOLEAN) {
								adataList.add(Boolean.toString(cell.getBooleanCellValue()));
							} else if (cellType == CellType.ERROR) {
								adataList.add(Integer.valueOf(cell.getErrorCellValue()).toString());
							} else if (cellType == CellType.FORMULA) {
								if (cell.getCachedFormulaResultType().equals(CellType.FORMULA)) {
									Double d = cell.getNumericCellValue();
									adataList.add(d.toString());
								} else if (cell.getCachedFormulaResultType().equals(CellType.STRING)) {
									String v = cell.getRichStringCellValue().toString();
									adataList.add(v.toString());
								}

							} else { // nothing29
							}
						} else {
							adataList.add(null);
						}
					}
					allString.add(adataList);
				}
			}
			return allString;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return allString;
	}
	
}
