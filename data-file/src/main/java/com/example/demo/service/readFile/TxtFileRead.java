package com.example.demo.service.readFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
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
 * 文件名 ： TxtFileRead.java
 * 包 名 ： com.example.demo.service.readFile
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年8月1日 下午5:40:36
 * 版 本 ： V1.0
 */
@Component
@Order(14)
public class TxtFileRead {
	
	@Autowired
	UserConfig userConfig;

	/**
	 * 方法名： readFileTxt
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param vo
	 * 参 数： @return
	 * 返 回： SysLoadFileInfoVo
	 * 作 者 ： Administrator
	 * @throws
	 */
	public void readFileTxt(SysLoadFileInfoVo vo) {
		SysLoadFileInfo info = vo.getInfo();
		String path = info.getPath();
		File file = new File(path);
		if (file.exists()) {
			if (info.getFileState() == null || FileState.UN_CONFIG.equals(info.getFileState())) {
				fileReadTopTen(file, vo);
			} else if (FileState.CONFIG.equals(info.getFileState())) {
				fileReadConfTopTen(file, vo);
			}
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
	private void fileReadConfTopTen(File file, SysLoadFileInfoVo vo) {
		List<String> rows = readTxtFileTopData(file, vo.getInfo());
		if (vo.getColumns() == null || vo.getColumns().size() == 0) {
			columnsSet(rows, vo);
		}
		rowsToDatas(rows, vo, vo.getColumns());

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
	private void columnsSet(List<String> rows, SysLoadFileInfoVo vo) {
		String[] frow_arr = rows.get(0).split(vo.getInfo().getSeparator());
		// columns
		List<SysLoadFileColsInfo> columns = new ArrayList<>();
		for (int i = 0; i < frow_arr.length; i++) {
			String string = frow_arr[i].replace(vo.getInfo().getEnclosed(), "");
			SysLoadFileColsInfo colsInfo = new SysLoadFileColsInfo();
			colsInfo.setUuid(UUID.randomUUID().toString());
			if ("Y".equals(vo.getInfo().getHasHead())) {
				colsInfo.setColumnName(string);
				colsInfo.setColumnDesc(string);
			} else {
				colsInfo.setColumnName("A_" + i);
				colsInfo.setColumnDesc("A_" + i);
			}
			colsInfo.setColumnLength(string.length() * 5);
			colsInfo.setColumnType("text");
			colsInfo.setDeleteFlag(0);
			colsInfo.setSort(i);
			colsInfo.setFileUuid(vo.getInfo().getUuid());

			columns.add(colsInfo);
		}
		vo.setColumns(columns);
		
	}

	/**
	 * @param sysLoadFileInfo
	 * 方法名： readTxtFileTopData
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param file
	 * 参 数： @return
	 * 返 回： List<String>
	 * 作 者 ： Administrator
	 * @throws
	 */
	private List<String> readTxtFileTopData(File file, SysLoadFileInfo sysLoadFileInfo) {
		file.setReadable(true);// 设置可读
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = "";
		ArrayList<String> allString = new ArrayList<>();
		try {
			Integer arows = sysLoadFileInfo.getSkip();
			if (arows == null) {
				arows = 0;
			}
			int crows = 0;
			// 读取到的内容给line变量 只读十条 ，跳过前面
			while ((line = br.readLine()) != null && userConfig.getReadRows() > allString.size()) {
				crows += 1;
				if (crows > arows) {
					allString.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allString;
	}

	/**
	 * 方法名： fileReadTopTen
	 * 功 能： 按照配置的读取前十条
	 * 参 数： @param file
	 * 参 数： @param vo
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	private void fileReadTopTen(File file, SysLoadFileInfoVo vo) {
		List<String> rows = readTxtFileTopData(file, vo.getInfo());
		guessConfig(file, rows, vo);

	}

	/**
	 * @param file
	 * 方法名： guessConfig
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param rows
	 * 参 数： @param vo
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	private void guessConfig(File file, List<String> rows, SysLoadFileInfoVo vo) {
		if (rows != null && rows.size() > 0) {
			// skip
			String frow = null;
			for (int i = 0; i < rows.size(); i++) {
				frow = rows.get(i);
				if (StringUtils.isNotBlank(frow)) {
					Integer skip = vo.getInfo().getSkip();
					if (skip == null) {
						skip = 0;
					}
					vo.getInfo().setSkip(i + skip);
					break;
				}
			}
			if (frow != null) {
				// Separator
				if (frow.split(",").length > 1) {
					vo.getInfo().setSeparator(",");
				} else if (frow.split("，").length > 1) {
					vo.getInfo().setSeparator("，");
				} else if (frow.split("	").length > 1) {
					vo.getInfo().setSeparator("	");
				} else if (frow.split("    ").length > 1) {
					vo.getInfo().setSeparator("    ");
				} else if (frow.split("  ").length > 1) {
					vo.getInfo().setSeparator("  ");
				}
				// Enclosed
				String[] frow_arr = frow.split(vo.getInfo().getSeparator());
				Character fchar = null;
				Integer aInteger = 0;
				for (String string : frow_arr) {
					Character tchar = string.charAt(0);
					if (fchar == null) {
						fchar = tchar;
					}
					if (fchar == tchar) {
						aInteger++;
					}
				}
				if (aInteger == frow_arr.length) {
					vo.getInfo().setEnclosed(fchar.toString());
				}

				// columns
				if (vo.getColumns() == null || vo.getColumns().size() == 0) {
					List<SysLoadFileColsInfo> columns = new ArrayList<>();
					for (int i = 0; i < frow_arr.length; i++) {
						String string = frow_arr[i].replace(vo.getInfo().getEnclosed(), "");
						
						SysLoadFileColsInfo colsInfo = new SysLoadFileColsInfo();
						colsInfo.setUuid(UUID.randomUUID().toString());
						if ("Y".equals(vo.getInfo().getHasHead())) {
							colsInfo.setColumnName(string);
							colsInfo.setColumnDesc(string);
						} else {
							colsInfo.setColumnName("A_" + i);
							colsInfo.setColumnDesc("A_" + i);
						}
						colsInfo.setColumnLength(string.length() * 5);
						colsInfo.setColumnType("text");
						colsInfo.setDeleteFlag(0);
						colsInfo.setSort(i);
						colsInfo.setFileUuid(vo.getInfo().getUuid());
						
						columns.add(colsInfo);
					}
					vo.setColumns(columns);
					
					rowsToDatas(rows, vo, columns);
//					vo.getInfo().setFileState(FileState.CONFIG);
				}
			} else {
				fileReadTopTen(file, vo);
			}

		}
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
	private void rowsToDatas(List<String> rows, SysLoadFileInfoVo vo, List<SysLoadFileColsInfo> columns) {
		// datas
		List<Map<String, String>> datas = new ArrayList<>();
		for (int i = 0; i < rows.size(); i++) {
			String row = rows.get(i);
			if (i == 0 && "Y".equals(vo.getInfo().getHasHead())) {
				continue;
			}
			String[] data_arr = row.split(vo.getInfo().getSeparator());
			Map<String, String> dataMap = new HashMap<>();
			for (int j = 0; j < data_arr.length; j++) {
				String string = data_arr[j];
				string = string.replace(vo.getInfo().getEnclosed(), "");
				if ("Y".equals(vo.getInfo().getHasHead())) {
					SysLoadFileColsInfo colsInfo = columns.get(j);
					if (colsInfo != null) {
						dataMap.put(columns.get(j).getColumnName(), string);
					} else {
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

}
