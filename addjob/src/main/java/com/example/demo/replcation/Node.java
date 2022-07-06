package com.example.demo.replcation;

import java.util.List;

/**
 * 文件名 ： Node.java
 * 包 名 ： com.example.demo.replcation
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年6月30日 下午3:57:41
 * 版 本 ： V1.0
 */
public class Node {
	
	private String		companyId;
	private String		name;
	private String		creditCode;
	private String		value;
	private Float		pers;
	private int			level;
	private List<Node>	children;
	
	/**
	 * 构造方法：
	 * 描 述： TODO(这里用一句话描述这个方法的作用)
	 * 参 数：
	 * 作 者 ： Administrator
	 * @throws
	 */
	public Node() {
	}
	
	/**
	 * 构造方法：
	 * 描 述： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param node
	 * 作 者 ： Administrator
	 * @throws
	 */
	public Node(Node node) {
		this.companyId = node.companyId;
		this.creditCode = node.creditCode;
		this.level = node.level;
		this.name = node.name;
	}
	
	/**
	 * 方法名 ： getCompanyId
	 * 功 能 ： 返回变量 companyId 的值
	 *
	 * @return: String
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * 方法名 ： setCompanyId
	 * 功 能 ： 设置变量 companyId 的值
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	/**
	 * 方法名 ： getName
	 * 功 能 ： 返回变量 name 的值
	 *
	 * @return: String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 方法名 ： setName
	 * 功 能 ： 设置变量 name 的值
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 方法名 ： getCreditCode
	 * 功 能 ： 返回变量 creditCode 的值
	 *
	 * @return: String
	 */
	public String getCreditCode() {
		return creditCode;
	}

	/**
	 * 方法名 ： setCreditCode
	 * 功 能 ： 设置变量 creditCode 的值
	 */
	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}
	
	/**
	 * 方法名 ： getValue
	 * 功 能 ： 返回变量 value 的值
	 *
	 * @return: String
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * 方法名 ： setValue
	 * 功 能 ： 设置变量 value 的值
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * 方法名 ： getPers
	 * 功 能 ： 返回变量 pers 的值
	 *
	 * @return: Float
	 */
	public Float getPers() {
		return pers;
	}
	
	/**
	 * 方法名 ： setPers
	 * 功 能 ： 设置变量 pers 的值
	 */
	public void setPers(Float pers) {
		this.pers = pers;
	}
	
	/**
	 * 方法名 ： getChildren
	 * 功 能 ： 返回变量 children 的值
	 *
	 * @return: List<Node>
	 */
	public List<Node> getChildren() {
		return children;
	}
	
	/**
	 * 方法名 ： setChildren
	 * 功 能 ： 设置变量 children 的值
	 */
	public void setChildren(List<Node> children) {
		this.children = children;
	}

	/**
	 * 方法名 ： getLevel
	 * 功 能 ： 返回变量 level 的值
	 *
	 * @return: int
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * 方法名 ： setLevel
	 * 功 能 ： 设置变量 level 的值
	 */
	public void setLevel(int level) {
		this.level = level;
	}

}
