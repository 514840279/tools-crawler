package com.example.demo.task;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.common.SearchParameters;

/**
 * 文件名 ： JobDao.java
 * 包 名 ： com.example.demo.task
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年6月24日 下午2:38:33
 * 版 本 ： V1.0
 */
@Mapper
public interface JobDao {

	Job findJob(@Param("id") Integer id);

	/**
	 * @param list
	 * 方法名： findAll
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param job
	 * 参 数： @param pageNumber
	 * 参 数： @param pageSize
	 * 参 数： @return
	 * 返 回： List<Job>
	 * 作 者 ： Administrator
	 * @throws
	 */
	List<Job> findAll(@Param("info") Job job, @Param("pageNumber") Integer pageNumber, @Param("pageSize") Integer pageSize, @Param("list") List<SearchParameters> list);

	/**
	 * 方法名： total
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param job
	 * 参 数： @param pageNumber
	 * 参 数： @param pageSize
	 * 参 数： @return
	 * 返 回： Integer
	 * 作 者 ： Administrator
	 * @throws
	 */
	Integer total(@Param("info") Job job, @Param("list") List<SearchParameters> list);
	
	/**
	 * 方法名： findOne
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param info
	 * 参 数： @return
	 * 返 回： Job
	 * 作 者 ： Administrator
	 * @throws
	 */
	Job findOne(@Param("info") Job info);

	/**
	 * 方法名： save
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param info
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	void save(@Param("info") Job info);

	/**
	 * 方法名： update
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param info
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	void update(@Param("info") Job info);

	/**
	 * 方法名： delete
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param id
	 * 返 回： void
	 * 作 者 ： Administrator
	 * @throws
	 */
	void delete(@Param("id") Integer id);

}
