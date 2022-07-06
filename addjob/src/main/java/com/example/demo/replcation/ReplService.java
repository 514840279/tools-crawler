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
		List<String> oldList = new ArrayList<>();
		List<String[]> data = new ArrayList<>();
		List<Map<String, String>> nodes = new ArrayList<>();
		// 向上去股东
		List<String> newList = new ArrayList<>();
		
		Node node = replDao.company(repl.getCompanyName());
		if (node != null) {

			List<Replcation> rel = new ArrayList<>();
			newList.add(node.getCompanyId());

			for (int i = 0; i < 10; i++) {
				if (newList.size() > 0) {
					newList = replLevel(oldList, data, nodes, newList, rel);
				} else {
					break;
				}
			}
			// 向下看控制、收益
			newList = new ArrayList<>();
			newList.add(node.getCompanyId());

			for (int i = 0; i < 10; i++) {
				if (newList.size() > 0) {
					newList = replLevel2(oldList, data, nodes, newList, rel);
				} else {
					break;
				}
			}

			map.put("rel", rel);
			map.put("data", data);
			map.put("nodes", nodes);
		}
		return map;
	}
	
	/**
	 * @param rel
	 * 方法名： replLevel2
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param oldList
	 * 参 数： @param data
	 * 参 数： @param nodes
	 * 参 数： @param newList
	 * 参 数： @return
	 * 返 回： List<String>
	 * 作 者 ： Administrator
	 * @throws
	 */
	private List<String> replLevel2(List<String> oldList, List<String[]> data, List<Map<String, String>> nodes, List<String> newList, List<Replcation> rel) {
		// 求股东
		List<Replcation> relList = replDao.repl2(newList);
		List<String> oth = new ArrayList<>();
		
		for (Replcation replcation : relList) {
			String[] replaStrings = new String[] { replcation.getPersonName(), replcation.getCompanyName() };
			boolean r = true;
			for (String[] string : data) {
				if (replaStrings[0].equals(string[0]) && replaStrings[1].equals(string[1])) {
					r = false;
				}
			}
			if (r) {
				data.add(replaStrings);
				rel.add(replcation);
			}
			
			if (!oldList.contains(replcation.getCompanyName()) && !oth.contains(replcation.getCompanyName())) {
				Map<String, String> nodemap = new HashMap<>();
				nodemap.put("id", replcation.getCompanyName());
//				nodemap.put("title", replcation.getCompanyName());
				nodemap.put("name", replcation.getCompanyName());
				nodemap.put("companyId", replcation.getCompanyId());
				nodemap.put("creditCode", replcation.getCreditCode());
				nodes.add(nodemap);
				oldList.add(replcation.getCompanyName());
				
				// 这里应该求的是最大的受益人 不是过半的控制人
				if (replcation.getPers() != null && replcation.getPers() >= 0.5 && replcation.getPersonId() != null) {
					oth = new ArrayList<>();
					oth.add(replcation.getPersonName());
				} else if (replcation.getPers() != null && replcation.getPers() == 0.5 && replcation.getPersonId() != null) {
					oth.add(replcation.getPersonName());
				}
			}

		}

		return oth;
	}
	
	/**
	 * @param rel
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
	private List<String> replLevel(List<String> oldList, List<String[]> data, List<Map<String, String>> nodes, List<String> newList, List<Replcation> rel) {
		
		// 求股东
		List<Replcation> relList = replDao.repl(newList);
		List<String> oth = new ArrayList<>();
		Float maxFloat = 0f;
		
		for (Replcation replcation : relList) {
			String[] replaStrings = new String[] { replcation.getPersonName(), replcation.getCompanyName() };
			boolean r = true;
			for (String[] string : data) {
				if (replaStrings[0].equals(string[0]) && replaStrings[1].equals(string[1])) {
					r = false;
				}
			}
			if (r) {
				data.add(replaStrings);
				rel.add(replcation);
			}
			
			if (!oldList.contains(replcation.getCompanyName()) && !oth.contains(replcation.getCompanyName())) {
				Map<String, String> nodemap = new HashMap<>();
				nodemap.put("id", replcation.getCompanyName());
//				nodemap.put("title", replcation.getCompanyName());
				nodemap.put("name", replcation.getCompanyName());
				nodemap.put("companyId", replcation.getCompanyId());
				nodemap.put("creditCode", replcation.getCreditCode());
				nodes.add(nodemap);
				oldList.add(replcation.getCompanyName());
			}
			
			if (!oldList.contains(replcation.getPersonName()) && !oth.contains(replcation.getPersonName())) {
				Map<String, String> nodemap = new HashMap<>();
				nodemap.put("id", replcation.getPersonName());
				nodemap.put("name", replcation.getPersonName());
				nodemap.put("subscribedAmount", replcation.getSubscribedAmount() == null ? null : replcation.getSubscribedAmount().toString());
				nodemap.put("pers", replcation.getPers() == null ? null : replcation.getPers().toString());
				nodemap.put("companyId", replcation.getPersonId());
				nodemap.put("creditCode", replcation.getPersonCode());
				nodes.add(nodemap);
				oldList.add(replcation.getPersonName());
				if (replcation.getPers() != null && replcation.getPers() > maxFloat && replcation.getPersonId() != null) {
					maxFloat = replcation.getPers();
					oth = new ArrayList<>();
					oth.add(replcation.getPersonId());
				} else if (replcation.getPers() != null && replcation.getPers() == maxFloat && replcation.getPersonId() != null) {
					oth.add(replcation.getPersonId());
				}
			}

		}

		return oth;
	}
	
	/**
	 * 方法名： echart1
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param repl
	 * 参 数： @return
	 * 返 回： Map<String,List<?>>
	 * 作 者 ： Administrator
	 * @throws
	 */
	public Map<String, ?> echart1(Replcation repl) {

		Node node = replDao.company(repl.getCompanyName());
		Node temp = node;
		List<Node> list1 = new ArrayList<>();
		List<Node> list2 = new ArrayList<>();
//		list1.add(temp);
//		list2.add(temp);

		// 最大请求成
		int level = 10;
		node.setChildren(toPNode(temp, level, list1));

		Node node2 = new Node(node);
		node2.setChildren(toCNode(temp, level, list2));

		Map<String, Object> result = new HashMap<>();
		result.put("data1", node);
		result.put("data2", node2);
//		result.put("rel1", list1);
//		result.put("rel2", list2);

		return result;
	}
	
	/**
	 * @param rel2
	 * @param level
	 * 方法名： toCNode
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param temp
	 * 参 数： @return
	 * 返 回： List<Node>
	 * 作 者 ： Administrator
	 * @throws
	 */
	private List<Node> toCNode(Node temp, int level, List<Node> rel2) {
		if (temp.getLevel() > level) {
			return null;
		}
		List<Node> list = replDao.toCNode(temp);
		
		// TODO 查询最大
		for (Node node2 : list) {
//			boolean b = true;
//			for (Node node3 : rel2) {
//				if (node3.getName().equals(node2.getName())) {
//					b = false;
//					break;
//				}
//			}
//			if (b) {
//				rel2.add(node2);
//			}
			node2.setChildren(toCNode(node2, level, rel2));
		}
		return list;
	}
	
	/**
	 * @param rel11
	 * @param rel1
	 * @param level
	 * 方法名： toPNode
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param node
	 * 参 数： @return
	 * 返 回： List<Node>
	 * 作 者 ： Administrator
	 * @throws
	 */
	private List<Node> toPNode(Node temp, int level, List<Node> list1) {
		if (temp.getLevel() > level) {
			return null;
		}
		List<Node> list = replDao.toPNode(temp);
		// 取最大
		float f = 0f;
		for (Node node : list) {
			// 最大比例
			if (node.getPers() != null && node.getPers() > f) {
				f = node.getPers();
			}
//			// 去重node
//			boolean b = true;
//			for (Node node2 : list1) {
//				if (node2.getName().equals(node.getName())) {
//					b = false;
//					break;
//				}
//			}
//			if (b) {
//				list1.add(node);
//			}

		}
		// 查询最大
		for (Node node2 : list) {
			if (node2.getPers() != null && node2.getPers() == f) {
				node2.setChildren(toPNode(node2, level, list1));
			}
		}
		
		return list;
	}
	
}
