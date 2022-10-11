package com.tools.json2obj.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.tools.json2obj.po.JsonImgData;
import com.tools.json2obj.po.JsonTableCofig;
import com.tools.json2obj.po.JsonTableColumn;
import com.tools.json2obj.po.JsonTableType;

public class SpellSql {

	// 基本信息
	public static String map2sql(Map<String, String> map, JsonTableCofig conf, List<JsonTableColumn> columns, String companyId) {
		
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into ");
		sb.append(conf.getTableName());
		StringBuffer columnsbuffer = new StringBuffer();
		StringBuffer valuesbuffer = new StringBuffer();
		columnsbuffer.append("(`company_id`");
		valuesbuffer.append("('" + companyId + "'");
		for (String key : map.keySet()) {
			boolean has = true;
			for (JsonTableColumn jsonTableColumn : columns) {
				if (key.equals(jsonTableColumn.getJsonName())) {
					columnsbuffer.append(",`" + jsonTableColumn.getColumnName() + "`");
					valuesbuffer.append(valueclean(map.get(key)));
					has = false;
					break;
				}
			}
			if (has) {
				// TODO
				System.out.println(String.format("{%s} 中{ %s }没有对应的关系", conf.getType(), key));
			}
		}

		columnsbuffer.append(")");
		valuesbuffer.append(")");
		sb.append(columnsbuffer);
		sb.append(" values ");
		sb.append(valuesbuffer);
		sb.append(";");

		return sb.toString();
	}

	// 股东
	public static String json2sql(JSONObject jsondata, Map<String, Object> delmap, JsonTableCofig conf, List<JsonTableColumn> columns, String companyId) {
		
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into ");
		sb.append(conf.getTableName());
		StringBuffer columnsbuffer = new StringBuffer();
		StringBuffer valuesbuffer = new StringBuffer();
		columnsbuffer.append("(`company_id`");
		valuesbuffer.append("('" + companyId + "'");
		for (JsonTableColumn jsonTableColumn : columns) {
			boolean has = true;
			for (String key : jsondata.keySet()) {
				if (key.equals(jsonTableColumn.getJsonName())) {
					columnsbuffer.append(",`" + jsonTableColumn.getColumnName() + "`");
					valuesbuffer.append(valueclean(jsondata.getString(key)));
					has = false;
					break;
				}
			}
			if (has && delmap != null) {
				for (String key : delmap.keySet()) {
					if (key.equals(jsonTableColumn.getJsonName())) {
						columnsbuffer.append(",`" + jsonTableColumn.getColumnName() + "`");
						valuesbuffer.append(valueclean(String.valueOf(delmap.get(key))));
						has = false;
						break;
					}
				}
			}
		}
		
		columnsbuffer.append(")");
		valuesbuffer.append(")");
		sb.append(columnsbuffer);
		sb.append(" values ");
		sb.append(valuesbuffer);
		sb.append(";");
		
		return sb.toString();
		
	}

	// 对数据简单的清理
	private static String valueclean(String v) {
		if (StringUtils.isBlank(v)) {
			return ",null";
		}
		v = v.trim();
		if ("-".equals(v) || "无".equals(v) || "没有".equals(v) || "空".equals(v) || "未填".equals(v)) {
			return ",null";
		}
		// TODO 处理 html
		v = v.replaceAll("<.*?>.*?</.*?>", "");
		
		return ",'" + v.replace("'", "‘") + "'";
	}

	// 对日期简单的清理
	private static String dateclean(Long v) {
		if (v == null) {
			return ",null";
		}
		// 一般long的为日期 s单位的数据转换成 yy-mm-dd
		Date d = new Date(v);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		return ",'" + sd.format(d).toString() + "'";
	}

	// 对图片简单处理
	private static String imageclean(String v) {
		if (StringUtils.isBlank(v)) {
			return ",null";
		}
		v = v.trim();
		if (v.startsWith("<img")) {
			//
			if (v.equals(JsonImgData.ds) || v.equals(JsonImgData.ds2)) {
				v = "董事";
			} else if (v.equals(JsonImgData.js) || v.equals(JsonImgData.js2)) {
				v = "监事";
			} else if (v.equals(JsonImgData.zjl) || v.equals(JsonImgData.zjl2)) {
				v = "总经理";
			} else if (v.equals(JsonImgData.zxds) || v.equals(JsonImgData.zxds2)) {
				v = "执行董事";
			} else if (v.equals(JsonImgData.dsz) || v.equals(JsonImgData.dsz2)) {
				v = "董事长";
			} else if (v.equals(JsonImgData.cwfzr)) {
				v = "财务负责人";
			} else if (v.equals(JsonImgData.jsz)) {
				v = "监事长";
			} else if (v.equals(JsonImgData.dsjzjl) || v.equals(JsonImgData.dsjzjl2)) {
				v = "董事兼总经理";
			} else if (v.equals(JsonImgData.zgjs) || v.equals(JsonImgData.zgjs2)) {
				v = "职工监事";
			} else if (v.equals(JsonImgData.jl) || v.equals(JsonImgData.jl2)) {
				v = "经理";
			} else if (v.equals(JsonImgData.fdsz)) {
				v = "副董事长";
			} else if (v.equals(JsonImgData.jshzx) || v.equals(JsonImgData.jshzx2)) {
				v = "监事会主席";
			} else if (v.equals(JsonImgData.dlds)) {
				v = "独立董事";
			}
			
		}

		return ",'" + v + "'";
	}
	
	// 一般处理
	public static String json2sql(JSONObject jsond, JsonTableCofig conf, List<JsonTableColumn> columns, String companyId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into ");
		sb.append(conf.getTableName());
		StringBuffer columnsbuffer = new StringBuffer();
		StringBuffer valuesbuffer = new StringBuffer();
		columnsbuffer.append("(`company_id`");
		valuesbuffer.append("('" + companyId + "'");
		if (conf.getType().equals(JsonTableType.TYPE_9)) {
			columnsbuffer.append(",`uuid`");
			valuesbuffer.append(",UUID()");
		}
		for (String key : jsond.keySet()) {
			boolean has = true;
			for (JsonTableColumn jsonTableColumn : columns) {
				if (key.equals(jsonTableColumn.getJsonName())) {
					columnsbuffer.append(",`" + jsonTableColumn.getColumnName() + "`");
					switch (jsonTableColumn.getType() == null ? "str" : jsonTableColumn.getType()) {
						case "date":
							valuesbuffer.append(dateclean((Long) jsond.get(key)));
							break;
						case "img":
							valuesbuffer.append(imageclean((String) jsond.get(key)));
							break;
						case "base64":
							valuesbuffer.append(base64clean((String) jsond.get(key)));
							break;
						default:
							valuesbuffer.append(valueclean((String) jsond.get(key)));
							break;
					}
					has = false;
					break;
				}
			}
			if (has) {
				// TODO
				System.out.println(String.format("{%s} 中{ %s }没有对应的关系,json值{%s}", conf.getType(), key, jsond.get(key)));
			}
		}
		
		columnsbuffer.append(")");
		valuesbuffer.append(")");
		sb.append(columnsbuffer);
		sb.append(" values ");
		sb.append(valuesbuffer);
		sb.append(";");
		
		return sb.toString();
	}
	
	public static String json2sql(JSONObject jsond, JsonTableCofig conf, List<JsonTableColumn> columns, String companyId, String year) {
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into ");
		sb.append(conf.getTableName());
		sb.append(year);
		StringBuffer columnsbuffer = new StringBuffer();
		StringBuffer valuesbuffer = new StringBuffer();
		columnsbuffer.append("(`company_id`");
		valuesbuffer.append("('" + companyId + "'");
		Integer yn = Integer.valueOf(year);
		if (yn < 2020 && (conf.getType().equals(JsonTableType.TYPE_6_1) || conf.getType().equals(JsonTableType.TYPE_6_8) || conf.getType().equals(JsonTableType.TYPE_6_3) || conf.getType().equals(JsonTableType.TYPE_6_4) || conf.getType().equals(JsonTableType.TYPE_6_5) || conf.getType().equals(JsonTableType.TYPE_6_6))) {
			columnsbuffer.append(",`id`");
			valuesbuffer.append(",UUID()");
		}
		for (String key : jsond.keySet()) {
			boolean has = true;
			if (columns != null) {
				for (JsonTableColumn jsonTableColumn : columns) {
					if (key.equals(jsonTableColumn.getJsonName())) {
						columnsbuffer.append(",`" + jsonTableColumn.getColumnName() + "`");
						switch (jsonTableColumn.getType() == null ? "str" : jsonTableColumn.getType()) {
							case "date":
								valuesbuffer.append(dateclean((Long) jsond.get(key)));
								break;
							case "img":
								valuesbuffer.append(imageclean((String) jsond.get(key)));
								break;
							case "base64":
								valuesbuffer.append(base64clean((String) jsond.get(key)));
								break;
							default:
								valuesbuffer.append(valueclean(String.valueOf(jsond.get(key))));
								break;
						}
						has = false;
						break;
					}
				}
			}

			if (has) {
				// TODO
				System.out.println(String.format("{%s} 中{ %s }没有对应的关系,json值{%s}", conf.getType(), key, jsond.get(key)));
			}
		}

		columnsbuffer.append(")");
		valuesbuffer.append(")");
		sb.append(columnsbuffer);
		sb.append(" values ");
		sb.append(valuesbuffer);
		sb.append(";");

		return sb.toString();
	}

	/**
	 * 方法名： base64clean
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param string
	 * 参 数： @return
	 * 返 回： Object
	 * 作 者 ： Administrator
	 * @throws
	 */
	private static String base64clean(String string) {
		String base64str = string.replace("<img src=\"data:image/png;base64,", "").replace("\"/>", "").replaceAll("\\n", "");

		Decoder base64 = Base64.getDecoder();
		byte[] str = base64.decode(base64str);
		String path = saveFile(str);

		String result = Ocr.readFile(path);
		if (result != null) {
			return ",'" + result + "'";
		} else {
			return ",null";
		}
	}

	/**
	 * 方法名： saveFile
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param str
	 * 参 数： @return
	 * 返 回： String
	 * 作 者 ： Administrator
	 * @throws
	 */
	private static String saveFile(byte[] str) {
		long date = System.currentTimeMillis();
		File file = new File(String.valueOf(date) + ".png");
		try (OutputStream os = new FileOutputStream(file)) {
			os.write(str, 0, str.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return file.getAbsolutePath();
	}
	
	public static void main(String[] args) {
		String ss = "iVBORw0KGgoAAAANSUhEUgAAAJEAAAANCAYAAACpfIwbAAAENElEQVR42u1Yf0TdURS/nueZSUzm" + "SRL9MUkmJjOZPKY/MknkSWYyMpmZZ8xMZjL2RyZJJPMkeUwyMxOT2R8zMfOkv2JmkjyRySTP4+3e" + "fG5OZ/d+773f/mns8OG9e8733vPrnnPvFeIkDUksSSxLPBBmqpWYlrgi7FQvMSfREVPGtcYt6PkW" + "cpSuSyyC91TiXCB/GHpF2TcEmXYDbzLCdy7772N9TjWQbTmlnqMSOYPcTYlZw3i3xEyA78VziRIW" + "uiuxA4doapIYk9iTqErciFB+FjLZQBmfNfIS23C4+naCGV2WeAxjt2CwL1/RPNZesuidwHdKpo/x" + "OjC/wkVHEpnsf4f1OdUZ/BFHzycSh+AJtq6Su8bGX0usevr+aFdU4GRNGYzV47/KujXIRCVROxLw" + "ICKJbDKuNQYkfkk0WObdkBgn/7swT6snXwdnA7Y3WqrgJvg8iZT+BdgWVY1s9ocmUaie7ZgnQ+SS" + "SPoiNjClfYmHnr4XvZicZ2gZHwtS9msikkh9vw4D9i1JFCXjWuMTKqaJmvANbzGHKP0uPg1OAQEy" + "rfUNlYxXoiTsGcAOXY+oEDb7Q5Mojp47bCP1ovJPw7+CJVybh+9P7MhmMpZCFg8b+rMtidQiH0gW" + "Z2PKmNZIQJ8eiRE48ZlE2rERvqB1uPg8OGqNXfiBnqcO0Kp4cHRSqPkvWxLWZX+cJArV85XEZ0P1" + "7IN/9Tw5HG98fH8s9APGNYNZQCW645lEGWR0Q0SC+MjY1khjrIjenMPvbTg5C32Fod/PefB5cJJw" + "4gjhvZF4CUfz4KyyeVS1mQq0P04SherZj4SoxX/V8gZJW+sjc8x7+v6YWsCoYrIcDB7yCHATdkMv" + "66fZQJmoNToxNs7k1ByP0EYqhrneI7guPg+OwG4rsnZ5yRCcRvzvBi+FVrJHKp+P/XGSKERP2mH6" + "IVMlibCCypRAm896+v4vSmKSJBa76hHgr9gNcwRl7M6pAJmoNfRYj6GS5DFeNVzZt7AhXHxTcNLQ" + "sQvnnBUSCBqcMfw3YTDAfrX7FywH8Sra5Gn0pFVTtbV70EuQFraJdqhif8HT91YagYN9qsQLZDBF" + "GcZMBMi4WmbJ0F6LmPu84caUJldXF98UHH2tXUQV6bIE5zt0SDGskvOPj/2TOFZwGjVczePoyZNl" + "mVWXVsjP4Kzo6/tjaiNKdkOZQc8kMtG+450otJ3psv0TJVsneoU8wi3gLFIHWxbwZCA8+abg6Cqw" + "wVqCDk6G3WIo3Y64gpvs1wfyPM5NCTwElgwbLVRPfnSpIDE7GW8H4+OBvj+iApl4Fw+O4owlUQLO" + "03qWmINU+f1I+GvsXcPFNwVHX29HLcHJR1znU7gljQXY308eCfX5dBLHi9PoyUlVvN+Wx1zTw6PL" + "9yeClxZnn1IOPWtJP4/DPwtUQ6rRv+T7//SfwukPllnuZ7DKQ20AAAAASUVORK5CYII=";
		String aaString = base64clean(ss);
		System.out.println(aaString);
	}

	public static String map2Upd(Map<String, String> map, JsonTableCofig conf, List<JsonTableColumn> columns, String companyId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" update ");
		sb.append(conf.getTableName());
		sb.append(" set ");
		sb.append(" `company_id` = '" + companyId + "'");
		for (String key : map.keySet()) {
			boolean has = true;
			for (JsonTableColumn jsonTableColumn : columns) {
				if (key.equals(jsonTableColumn.getJsonName())) {
					String val = map.get(key);
					if (StringUtils.isNotBlank(val)) {
						sb.append(", `" + jsonTableColumn.getColumnName() + "` = '" + val.toString().replace("'", "\"").trim() + "'");
					}
					has = false;
					break;
				}
			}
			if (has) {
				// TODO
				System.out.println(String.format("{%s} 中{ %s }没有对应的关系", conf.getType(), key));
			}
		}
		sb.append(" where `company_id` = '" + companyId + "'");
		sb.append(";");

		return sb.toString();
	}
	
}
