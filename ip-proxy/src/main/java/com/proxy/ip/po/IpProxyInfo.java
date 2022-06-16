package com.proxy.ip.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "ip_proxy_info", uniqueConstraints = @UniqueConstraint(columnNames = { "ip", "port" }))
public class IpProxyInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer	id;
	private String	ip;
	private Integer	port;
	private Double	responseTime;
	private String	country;
	// 插入时间
	@Column(name = "create_time", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@org.hibernate.annotations.CreationTimestamp
	@DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date	createTime;
	
	// 更新时间
	@Column(name = "update_time", updatable = false, insertable = false) // 这里应用数据库更行策略 ON UPDATE CURRENT_TIMESTAMP 所以无需jpa插座
	@Temporal(TemporalType.TIMESTAMP)
	@org.hibernate.annotations.UpdateTimestamp
	@LastModifiedDate
	@DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date	updateTime;

	private Integer	deleteFlag;

	public IpProxyInfo() {
		super();
	}

	public IpProxyInfo(Integer id, String ip, Integer port) {
		super();
		this.id = id;
		this.ip = ip;
		this.port = port;
	}

	@Override
	public String toString() {
		return "IpProxyInfo [id=" + id + ", ip=" + ip + ", port=" + port + ", responseTime=" + responseTime + ", createTime=" + createTime + ", updateTime=" + updateTime + ", deleteFlag=" + deleteFlag + "]";
	}

	public IpProxyInfo(String ip, Integer port, Double responseTime, String country, Integer deleteFlag) {
		super();
		this.ip = ip;
		this.port = port;
		this.responseTime = responseTime;
		this.country = country;
		this.deleteFlag = deleteFlag;
	}

	public IpProxyInfo(String ip, Integer port, Integer deleteFlag) {
		super();
		this.ip = ip;
		this.port = port;
		this.deleteFlag = deleteFlag;
	}

}
