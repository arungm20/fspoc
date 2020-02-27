package com.infosys.fs.brokerservice.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class BrokerServiceSender {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BrokerServiceSender.class);
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void enqueFraudCheckRequest(String message) {
		
		LOGGER.info("sending message='{}'", message);
		jmsTemplate.convertAndSend("fcs.sender.queue", message);
	}
}
