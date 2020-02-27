package com.infosys.fs.paymentprocessingsys;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.opentracing.starter.CamelOpenTracing;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.connection.JmsTransactionManager;

@SpringBootApplication
@CamelOpenTracing
@ComponentScan(basePackages = { "com.infosys.fs" })
public class PaymentProcessingSysApplication {
	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(PaymentProcessingSysApplication.class, args);
		
	}
}
