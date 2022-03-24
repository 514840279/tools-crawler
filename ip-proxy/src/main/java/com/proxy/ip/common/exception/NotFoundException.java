package com.proxy.ip.common.exception;

/**
 * 文件名 ： NotFoundException.java
 * 包 名 ： com.proxy.ip.common.exception
 * 描 述 ： 自定义异常信息
 * 作 者 ： Administrator
 * 时 间 ： 2022年3月23日 上午9:40:14
 * 版 本 ： V1.0
 */

public class NotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotFoundException() {
	}

	public NotFoundException(String format) {
		super(format);
	}

}
