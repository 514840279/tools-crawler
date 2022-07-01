package com.example.demo.replcation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.BaseResult;
import com.example.demo.common.ResultUtil;

/**
 * 文件名 ： ReplColtroller.java
 * 包 名 ： com.example.demo.replcation
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年6月30日 下午3:52:51
 * 版 本 ： V1.0
 */
@RequestMapping("/repl")
@RestController
public class ReplColtroller {
	
	@Autowired
	ReplService replService;
	
	@PostMapping("/chart")
	public BaseResult<Map<String, List<?>>> chart(@RequestBody Replcation repl) {
		try {
			Map<String, List<?>> map = replService.chart(repl);
			return ResultUtil.success(map);
		} catch (Exception e) {
			return ResultUtil.error(e.getMessage());
		}
	}
	
}
