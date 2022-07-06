package com.example.demo.replcation;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 文件名 ： ReplDao.java
 * 包 名 ： com.example.demo.replcation
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年6月30日 下午3:53:23
 * 版 本 ： V1.0
 */
@Mapper
public interface ReplDao {

	/**
	 * 方法名： repl
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param companyName
	 * 参 数： @return
	 * 返 回： List<Replcation>
	 * 作 者 ： Administrator
	 * @throws
	 */
	List<Replcation> repl(@Param("list") List<String> newList);
	
	/**
	 * 方法名： repl2
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param companyName
	 * 参 数： @return
	 * 返 回： List<Replcation>
	 * 作 者 ： Administrator
	 * @throws
	 */
	List<Replcation> repl2(@Param("list") List<String> relList);

	Node company(@Param("companyName") String companyName);
	
	/**
	 * 方法名： toCNode
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param temp
	 * 参 数： @return
	 * 返 回： List<Node>
	 * 作 者 ： Administrator
	 * @throws
	 */
	List<Node> toCNode(@Param("node") Node temp);

	/**
	 * 方法名： toPNode
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param temp
	 * 参 数： @return
	 * 返 回： List<Node>
	 * 作 者 ： Administrator
	 * @throws
	 */
	List<Node> toPNode(@Param("node") Node temp);
	
}
