package com.infosys.fs.paymentprocessingsys.routing;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.infosys.fs.paymentprocessingsys.validation.RequestValidator;

@Component
public class PPSMessagingRouter extends RouteBuilder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PPSMessagingRouter.class);

	/**
	 * Route Method to receive message, validate and enqueue to BS request Queue
	 */
	
	@Override
	public void configure() throws Exception {
		
		from("{{pps.messaging.request.input.queue}}")
			.log(LoggingLevel.INFO, LOGGER, "PPS : New Request message received")
			.process(exchange -> {
					LOGGER.info("Received : {}", exchange.getMessage().getBody());
					RequestValidator.validatePaymentRequest(exchange);
			})
			.choice()
				.when(simple("${body} contains 'REJECTED'"))
					.log(LoggingLevel.ERROR, LOGGER, "PPS : Invalid Request")
					.to("{{pps.messaging.response.rejected.output.queue}}")
				.otherwise()
					.to("{{pps.messaging.request.output.queue}}")
					.log(LoggingLevel.INFO, LOGGER, "PPS : Request Message is successfully sent to the output queue")
			
			.end();
		
		from("{{pps.messaging.response.input.queue}}")
			.log(LoggingLevel.INFO, LOGGER, "PPS : New Response message received")
			.choice()
				.when(simple("${body} contains 'REJECTED'"))
					.process(exchange -> {
						LOGGER.info("Sending to Reject Queue : {}", exchange.getMessage().getBody());
					})
					.to("{{pps.messaging.response.rejected.output.queue}}")
				.otherwise()
					.process(exchange -> {
						LOGGER.info("Sending to Approved Queue : {}", exchange.getMessage().getBody());
					})
					.to("{{pps.messaging.response.approved.output.queue}}")
					.log(LoggingLevel.INFO, LOGGER, "PPS : Response Message is successfully sent to the output queue")
			.end();
		
	}
	
}
