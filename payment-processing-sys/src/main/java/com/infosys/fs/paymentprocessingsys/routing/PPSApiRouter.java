package com.infosys.fs.paymentprocessingsys.routing;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.infosys.fs.paymentprocessingsys.handler.PaymentProcessHandler;
import com.infosys.fs.paymentprocessingsys.model.PaymentRequest;
import com.infosys.fs.paymentprocessingsys.model.PaymentResponse;

@Component
public class PPSApiRouter extends RouteBuilder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PPSApiRouter.class);
	
	@Autowired
	private PaymentProcessHandler paymentProcessHandler;
	

	/**
	 * Route Method to consume Request from API Queue, validate and send to Broker Service
	 */
	@Override
	public void configure() throws Exception {
		from("{{pps.api.request.input.queue}}").log(LoggingLevel.INFO, LOGGER, "New message received")
			.choice()
			.when(header("x-retry-count").isNotNull())
				.process(exchange -> {
					LOGGER.info("Received request to retry: {}", exchange.getMessage().getBody());
					Gson gson = new Gson();
					PaymentResponse paymentResponse = gson.fromJson((String) exchange.getMessage().getBody(),
							PaymentResponse.class);
					paymentProcessHandler.retryPaymentProcessCheck(exchange.getMessage(),paymentResponse.getId());
				})
			.otherwise()
				.process(exchange -> {
					LOGGER.info("Received : {}", exchange.getMessage().getBody());
					Gson gson = new Gson();
					PaymentRequest paymentRequest = gson.fromJson((String) exchange.getMessage().getBody(),
							PaymentRequest.class);
					paymentProcessHandler.performNextStep(exchange.getMessage(), paymentRequest, 0);
				}).end();
		
	}
	
}
