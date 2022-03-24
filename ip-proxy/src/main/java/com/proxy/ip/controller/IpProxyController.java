package com.proxy.ip.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proxy.ip.common.BaseResult;
import com.proxy.ip.common.ResultUtil;
import com.proxy.ip.common.exception.NotFoundException;
import com.proxy.ip.po.IpProxyInfo;
import com.proxy.ip.service.IpProxyService;

@RestController
@RequestMapping(name = "ip代理", value = "/ip")
public class IpProxyController {
	
	private static final Logger	logger	= LoggerFactory.getLogger(IpProxyController.class);
	
	@Autowired
	IpProxyService				ipProxyService;

	@RequestMapping(name = "查询多个ip", path = "/findList", method = { RequestMethod.GET, RequestMethod.POST }) //
	public BaseResult<List<IpProxyInfo>> findList(
	        // 每次返回数量，默认100
	        @RequestParam(value = "size", defaultValue = "100") Integer size
			// 有效时长 s
	        , @RequestParam(value = "longTime", defaultValue = "240") Integer longTime
	//
	) {
		logger.info("findList{}:{}", size, longTime);
		
		try {
			List<IpProxyInfo> list = ipProxyService.findList(size, longTime);
			return ResultUtil.success(list);
		} catch (Exception e) {
			return ResultUtil.error(e.getMessage());
		}

	}

	@RequestMapping(name = "更新單個ip", path = "/update", method = { RequestMethod.PUT }) //
	public BaseResult<String> update(
	        // 主鍵
	        @RequestParam(value = "id") Integer id
			// flag
	        , @RequestParam(value = "deleteFlag", defaultValue = "1") Integer deleteFlag
	//
	) {
		logger.info("update{}:{}", id, deleteFlag);
		
		try {
			ipProxyService.update(id, deleteFlag);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(e.getMessage());
		}

	}
	
	@RequestMapping(name = "刪除某個", path = "/deleteOne", method = { RequestMethod.PUT }) //
	public BaseResult<String> deleteOne(
	        // 主鍵
	        @RequestParam(value = "id") Integer id
	//
	) {
		logger.info("update{}:{}", id);
		
		try {
			ipProxyService.deleteOne(id);
			return ResultUtil.success();
		} catch (Exception e) {
			return ResultUtil.error(e.getMessage());
		}

	}
	
	@RequestMapping(name = "随机取并且不能重复", path = "/radom", method = { RequestMethod.GET }) //
	public ResponseEntity<String> radom() throws NotFoundException {
		logger.info("radom");
		String proxy = null;
		try {
			proxy = ipProxyService.radom();
		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok(proxy);

	}
	
}
