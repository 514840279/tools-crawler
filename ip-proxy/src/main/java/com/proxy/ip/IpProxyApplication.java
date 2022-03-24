package com.proxy.ip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IpProxyApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(IpProxyApplication.class, args);
	}
	
}
