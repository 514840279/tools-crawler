package com.proxy.ip.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proxy.ip.common.UserConfig;
import com.proxy.ip.dao.IpProxyInfoDao;
import com.proxy.ip.po.IpProxyInfo;

@Service
public class IpProxyService {
	
	@Autowired
	IpProxyInfoDao	ipProxyInfoDao;
	
	@Autowired
	UserConfig		userConfig;

	public List<IpProxyInfo> findList(Integer size, Integer longTime) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Long time = startTime - userConfig.getLongTime() * 1000;
		Date d = new Date(time);
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<IpProxyInfo> list = ipProxyInfoDao.findList(size, myFormatter.format(d));
		if (list == null || list.size() < size) {
			
		}
		return list;
	}

	public void update(Integer id, Integer deleteFlag) {
		ipProxyInfoDao.update(id, deleteFlag);
	}

	public void deleteAllByDeleteFlag(Integer deleteFlag) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Long time = startTime - userConfig.getLongTime() * 1000;
		Date d = new Date(time);
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ipProxyInfoDao.deleteAllByDeleteFlag(deleteFlag, myFormatter.format(d));
	}

	public void deleteOne(Integer id) {
		ipProxyInfoDao.deleteOne(id);
	}
	
	@Transactional
	public String radom() {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Long time = startTime - userConfig.getLongTime() * 1000;
		Date d = new Date(time);
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		IpProxyInfo info = ipProxyInfoDao.findOne(myFormatter.format(d));
		if (info != null) {
			ipProxyInfoDao.update(info.getId(), 2);
			return info.getIp() + ":" + info.getPort();
		} else if (userConfig.getStartUpd()) {
			ipProxyInfoDao.updateDuring(userConfig.getBeforTime(), 2);
			return radom();
		} else {
			return "沒有ip";
		}
	}
	
}
