package com.example.demo.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.task.Job;

/**
 * 文件名 ： SearchService.java
 * 包 名 ： com.example.demo.search
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年6月24日 下午5:54:38
 * 版 本 ： V1.0
 */
@Service
public class SearchService {
	@Autowired
	SearchDao searchDao;
	
	/**
	 * 方法名： annualList
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param job
	 * 参 数： @return
	 * 返 回： List<AnnualReport>
	 * 作 者 ： Administrator
	 * @throws
	 */
	public List<AnnualReport> annualList(Job job) {
		Base base = searchDao.companyInfo(job);
		if (base != null) {
			return searchDao.annualList(base);
		} else {
			return null;
		}
	}

	/**
	 * 方法名： reportList
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param annualReport
	 * 参 数： @return
	 * 返 回： List<AnnualReportSummary>
	 * 作 者 ： Administrator
	 * @throws
	 */
	public AnnualReportSummary reportList(AnnualReport annualReport) {
		return searchDao.reportList(annualReport);
	}

}
