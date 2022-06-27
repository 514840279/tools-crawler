package com.example.demo.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.BaseResult;
import com.example.demo.common.ResultUtil;
import com.example.demo.task.Job;

/**
 * 文件名 ： SearchController.java
 * 包 名 ： com.example.demo.search
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年6月24日 下午5:36:04
 * 版 本 ： V1.0
 */
@RestController
@RequestMapping("/search")
public class SearchController {
	@Autowired
	SearchService searchService;

	@PostMapping("/annualList")
	public BaseResult<List<AnnualReport>> annualList(@RequestBody Job job) {
		try {
			List<AnnualReport> li = searchService.annualList(job);
			return ResultUtil.success(li);
		} catch (Exception e) {
			return ResultUtil.error(e.getMessage());
		}
		
	}

	@PostMapping("/reportList")
	public BaseResult<AnnualReportSummary> reportList(@RequestBody AnnualReport annualReport) {
		try {
			AnnualReportSummary li = searchService.reportList(annualReport);
			return ResultUtil.success(li);
		} catch (Exception e) {
			return ResultUtil.error(e.getMessage());
		}
		
	}
}
