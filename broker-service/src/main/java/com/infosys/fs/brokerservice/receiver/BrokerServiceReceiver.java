package com.infosys.fs.brokerservice.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.infosys.fs.brokerservice.mapper.FraudCheckMapper;
import com.infosys.fs.brokerservice.sender.BrokerServiceSender;

@Service
public class BrokerServiceReceiver {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BrokerServiceReceiver.class);
	
	
	@JmsListener(destination = "fcs.receiver.queue")
	public void receiveFcsResult(String text) {
		// Receive XML and convert to Required format
		LOGGER.info("Message Received: {}", text);
	}
}
