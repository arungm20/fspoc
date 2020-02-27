package com.infosys.fs.fcs.fraudchecksystem;

import org.apache.camel.opentracing.starter.CamelOpenTracing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@CamelOpenTracing
@ComponentScan(basePackages = { "com.infosys.fs" })
public class FraudCheckSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FraudCheckSystemApplication.class, args);
	}

}
