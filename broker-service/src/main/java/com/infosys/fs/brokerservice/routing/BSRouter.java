package com.infosys.fs.brokerservice.routing;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.infosys.fs.brokerservice.handler.BrokerServiceHandler;
import com.infosys.fs.brokerservice.mapper.FraudCheckMapper;
import com.infosys.fs.brokerservice.model.PaymentRequest;

import io.opentracing.Tracer;


@Component
public class BSRouter extends RouteBuilder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BSRouter.class);
	
	@Autowired
	private BrokerServiceHandler brokerServiceHandler;
	
	@Override
	public void configure() throws Exception {
		
		from("{{bs.request.input.queue}}").log(LoggingLevel.INFO, LOGGER, "BS : New Request message received")
		.process(exchange -> {
			LOGGER.info("BS Received : {}", exchange.getMessage().getBody());
			Gson gson = new Gson();
			PaymentRequest paymentRequest = gson.fromJson((String) exchange.getMessage().getBody(),
					PaymentRequest.class);
			
			exchange.getMessage().setBody(FraudCheckMapper.mapRequest(paymentRequest));
		}).to("{{bs.request.output.queue}}")
		  .log(LoggingLevel.INFO, LOGGER, "BS: Request Message is successfully sent to the output queue")
		  .end();
		
		from("{{bs.response.input.queue}}")
		.log(LoggingLevel.INFO, LOGGER, "BS : New Response message received")
		.choice()
			.when(header("x-reply-to").isEqualTo("api"))
			.process(exchange -> {
				LOGGER.info("BS Received : {}", exchange.getMessage().getBody());
				LOGGER.info("BS Sending to PPS via API");
				brokerServiceHandler.sendToPps(exchange.getMessage().getBody());
				
			})
			.otherwise()
			.process(exchange -> {
				LOGGER.info("BS Received : {}", exchange.getMessage().getBody());
				exchange.getMessage().setBody(FraudCheckMapper.mapResponse(exchange.getMessage().getBody()));
			}).to("{{bs.response.output.queue}}")
				.log(LoggingLevel.INFO, LOGGER, "BS: Response Message is successfully sent to the output queue").end();
		
	}
	
	/*
	 * private void trace(Exchange exchange, String operationName) { String
	 * jmsCorrelationId = (String)
	 * exchange.getIn().getHeader("JMSCorrelationID");
	 * LOGGER.info("JMSCorrelationID Header :  {} ", jmsCorrelationId);
	 * 
	 * try (Scope scope =
	 * tracer.buildSpan("{{info.app.name}}").startActive(true)) {
	 * 
	 * scope.span().setBaggageItem("conversationId", jmsCorrelationId);
	 * scope.span().setOperationName(operationName);
	 * scope.span().setTag("conversationId", jmsCorrelationId); }
	 * 
	 * }
	 */
}
