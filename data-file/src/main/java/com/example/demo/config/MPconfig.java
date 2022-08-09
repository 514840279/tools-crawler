package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

@Configuration
public class MPconfig {
	
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		// 1、定义MP拦截器
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 2、添加具体的拦截器
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
		return interceptor;
	}
}
