package com.infosys.fs.fcs.fraudchecksystem.routing;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.infosys.fs.fcs.fraudchecksystem.service.FCSService;

import io.opentracing.Tracer;

@Component
public class FCSRouter extends RouteBuilder {
	
	@Autowired
	private FCSService fcsService;
	
	@Value("${info.app.name}")
	private String appName;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FCSRouter.class);
	
	@Override
	public void configure() throws Exception {
		
		from("{{fcs.request.input.queue}}").log(LoggingLevel.INFO, LOGGER, "FCS : New message received")
		.process(exchange -> {
			LOGGER.info("FCS Received : {}", exchange.getMessage().getBody());
			exchange.getMessage()
					.setBody(fcsService.performFraudCheck(exchange.getMessage().getBody()));
		}).to("{{fcs.response.output.queue}}")
		.log(LoggingLevel.INFO, LOGGER, "FCS : Message is successfully sent to the output queue")
		.end();
		
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
