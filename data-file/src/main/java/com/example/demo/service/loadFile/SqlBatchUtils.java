package com.example.demo.service.loadFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.chuxue.application.common.base.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.po.SysLoadFileColsMapping;

/**
 * 文件名 ： SqlBatchUtils.java
 * 包 名 ： com.example.demo.service.loadFile
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年8月5日 下午5:26:03
 * 版 本 ： V1.0
 */
@Component
@Order(50)
public class SqlBatchUtils {
	
	private static final Logger	logger	= LoggerFactory.getLogger(SqlBatchUtils.class);

	@Autowired
	@Resource(name = "jdbcTemplate")
	JdbcTemplate				connection;
	
	public Long batchInsert(String sqlstr, List<Map<String, String>> dataList, List<SysLoadFileColsMapping> mappings) {
		if (dataList == null || dataList.size() == 0) {
			logger.info("BatchInsertUtil batchInsert list data is null!");
			return 0L;
		}
		try {
			// 2.获得PreparedStatement,预编译SQL语句
			List<Object[]> batchArgs = new ArrayList<>();
			for (Map<String, String> data : dataList) {
				Object[] args = new Object[mappings.size()];
				for (int i = 0; i < mappings.size(); i++) {
					SysLoadFileColsMapping maping = mappings.get(i);
					for (String key : data.keySet()) {
						if (key.equals(maping.getTabsColumnUuid())) {
							args[i] = data.get(key);
						}
					}
				}
				batchArgs.add(args);
			}
			connection.batchUpdate(sqlstr, batchArgs);
		} catch (Exception e) {
			throw new BaseException("BatchInsertUtil batchInsert is exception！" + e.getMessage());
		}
		return null;
	}
}
