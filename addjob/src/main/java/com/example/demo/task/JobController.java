package com.example.demo.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.BaseResult;
import com.example.demo.common.Pagination;
import com.example.demo.common.ResultUtil;

/**
 * 文件名 ： JobController.java
 * 包 名 ： com.example.demo.task
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年6月24日 下午2:29:41
 * 版 本 ： V1.0
 */
@RestController
@RequestMapping("/job")
public class JobController {

	@Autowired
	JobService jobService;
	
	@PostMapping("/findOne")
	public BaseResult<Job> findOne(@RequestBody Job info) {
		try {
			Job s = jobService.findOne(info);
			return ResultUtil.success(s);
		} catch (Exception e) {
			return ResultUtil.error(e.getMessage());
		}
	}
	
	@PostMapping("/save")
	public BaseResult<Job> save(@RequestBody Job info) {
		try {
			Job s = jobService.save(info);
			return ResultUtil.success(s);
		} catch (Exception e) {
			return ResultUtil.error(e.getMessage());
		}
	}
	
	@PostMapping("/page")
	public BaseResult<Pagination<Job>> page(@RequestBody Pagination<Job> vo) {
		try {
			Pagination<Job> re = jobService.page(vo);
			return ResultUtil.success(re);
		} catch (Exception e) {
			return ResultUtil.error(e.getMessage());
		}
	}
	
	@DeleteMapping("/delete")
	public BaseResult<String> delete(@RequestBody Job info) {
		try {
			String re = jobService.delete(info);
			return ResultUtil.success(re);
		} catch (Exception e) {
			return ResultUtil.error(e.getMessage());
		}
	}
	
}
