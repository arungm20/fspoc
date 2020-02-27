package com.infosys.fs.brokerservice;

import org.apache.camel.opentracing.starter.CamelOpenTracing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@CamelOpenTracing
@ComponentScan(basePackages = { "com.infosys.fs" })
public class BrokerServiceApplication {
	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(BrokerServiceApplication.class, args);
	}
	
}
