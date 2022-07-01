package com.example.demo.replcation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文件名 ： ReplService.java
 * 包 名 ： com.example.demo.replcation
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年6月30日 下午3:53:10
 * 版 本 ： V1.0
 */
@Service
public class ReplService {
	@Autowired
	ReplDao replDao;
	
	/**
	 * 方法名： chart
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param repl
	 * 参 数： @return
	 * 返 回： Map<String,List<Object>>
	 * 作 者 ： Administrator
	 * @throws
	 */
	public Map<String, List<?>> chart(Replcation repl) {
		Map<String, List<?>> map = new HashMap<>();
		if (repl.getLevel() == null) {
			repl.setLevel(3);
		}
		List<String> oldList = new ArrayList<>();
		List<String[]> data = new ArrayList<>();
		List<Map<String, String>> nodes = new ArrayList<>();
		List<String> newList = new ArrayList<>();
		newList.add(repl.getCompanyName());
		for (int i = 0; i < repl.getLevel(); i++) {
			newList = replLevel(oldList, data, nodes, newList);
		}
		
		map.put("data", data);
		map.put("nodes", nodes);

		return map;
	}

	/**
	 * @return
	 * 方法名： replLevel
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param oldList
	 * 参 数： @param data
	 * 参 数： @param nodes
	 * 参 数： @param newList
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	private List<String> replLevel(List<String> oldList, List<String[]> data, List<Map<String, String>> nodes, List<String> newList) {
		// 求股东
		List<Replcation> relList = replDao.repl(newList);
		List<String> oth = new ArrayList<>();
		for (Replcation replcation : relList) {
			String[] replaStrings = new String[] { replcation.getPersonName(), replcation.getCompanyName() };
			if (!data.contains(replaStrings)) {
				data.add(replaStrings);
			}
			
			if (!oldList.contains(replcation.getCompanyName())) {
				Map<String, String> nodemap = new HashMap<>();
				nodemap.put("id", replcation.getCompanyName());
				nodemap.put("title", replcation.getCompanyName());
				nodemap.put("name", replcation.getCompanyName());
				oldList.add(replcation.getCompanyName());
				nodes.add(nodemap);
				oth.add(replcation.getCompanyName());
			}

			if (!oldList.contains(replcation.getPersonName())) {
				Map<String, String> nodemap = new HashMap<>();
				nodemap.put("id", replcation.getPersonName());
				nodemap.put("title", replcation.getPersonName());
				nodemap.put("name", replcation.getPersonName());
				oldList.add(replcation.getPersonName());
				nodes.add(nodemap);
				oth.add(replcation.getPersonName());
			}
		}
		// 求投资
		relList = replDao.repl2(relList);
		
		for (Replcation replcation : relList) {
			String[] replaStrings = new String[] { replcation.getPersonName(), replcation.getCompanyName() };
			if (!data.contains(replaStrings)) {
				data.add(replaStrings);
			}
			
			if (!oldList.contains(replcation.getCompanyName())) {
				Map<String, String> nodemap = new HashMap<>();
				nodemap.put("id", replcation.getCompanyName());
				nodemap.put("title", replcation.getCompanyName());
				nodemap.put("name", replcation.getCompanyName());
				oldList.add(replcation.getCompanyName());
				nodes.add(nodemap);
				oth.add(replcation.getCompanyName());
			}

			if (!oldList.contains(replcation.getPersonName())) {
				Map<String, String> nodemap = new HashMap<>();
				nodemap.put("id", replcation.getPersonName());
				nodemap.put("title", replcation.getPersonName());
				nodemap.put("name", replcation.getPersonName());
				oldList.add(replcation.getPersonName());
				nodes.add(nodemap);
				oth.add(replcation.getPersonName());
			}
		}
		return oth;
	}

}
