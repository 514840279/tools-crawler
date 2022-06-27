package com.example.demo.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.Pagination;

/**
 * 文件名 ： JobService.java
 * 包 名 ： com.example.demo.task
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年6月24日 下午2:35:39
 * 版 本 ： V1.0
 */
@Service
public class JobService {
	@Autowired
	JobDao jobDao;

	/**
	 * 方法名： te
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @return
	 * 返 回： Job
	 * 作 者 ： Administrator
	 * @throws
	 */
	public Job te() {
		return jobDao.findJob(13766);
	}
	
	/**
	 * 方法名： page
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param vo
	 * 参 数： @return
	 * 返 回： Pagination<Job>
	 * 作 者 ： Administrator
	 * @throws
	 */
	public Pagination<Job> page(Pagination<Job> vo) {
		Job job = vo.getInfo();

		List<Job> list = jobDao.findAll(job, vo.getPageNumber(), vo.getPageSize(), vo.getSearchList());
		
		vo.setContent(list);
		Integer count = jobDao.total(job, vo.getSearchList());
		vo.setTotalElements(count);
		return vo;
	}
	
	/**
	 * 方法名： findOne
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param info
	 * 参 数： @return
	 * 返 回： Job
	 * 作 者 ： Administrator
	 * @throws
	 */
	public Job findOne(Job info) {
		return jobDao.findOne(info);
	}
	
	/**
	 * 方法名： save
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param info
	 * 参 数： @return
	 * 返 回： Job
	 * 作 者 ： Administrator
	 * @throws
	 */
	public Job save(Job info) {
		if (info.getId() == null) {
			jobDao.save(info);
		} else {
			Job re = jobDao.findJob(info.getId());
			if (re == null) {
				jobDao.save(info);
			} else {
				jobDao.update(info);
			}
		}

		return info;
	}
	
	/**
	 * 方法名： delete
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param info
	 * 参 数： @return
	 * 返 回： String
	 * 作 者 ： Administrator
	 * @throws
	 */
	public String delete(Job info) {
		jobDao.delete(info.getId());
		return "OK";
	}

}
