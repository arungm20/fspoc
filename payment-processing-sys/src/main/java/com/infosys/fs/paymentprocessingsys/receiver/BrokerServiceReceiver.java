package com.infosys.fs.paymentprocessingsys.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.fs.paymentprocessingsys.mapper.FraudCheckRequestMapper;
import com.infosys.fs.paymentprocessingsys.sender.BrokerServiceSender;

@Service
public class BrokerServiceReceiver {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BrokerServiceReceiver.class);
	
	@Autowired
	private BrokerServiceSender brokerServiceSender;
	
	//@JmsListener(destination = "pps.receiver.queue")
	public void receivePPSRequest(String ppsRequest) {
		// Receive JSON -> Convert to XML to Enque
		LOGGER.info("Request Received to Perform Fraud Check: {}", ppsRequest);
		
		FraudCheckRequestMapper.map(ppsRequest);
		brokerServiceSender.enqueFraudCheckRequest(ppsRequest);
	}
	
	//@JmsListener(destination = "fcs.receiver.queue")
	public void receiveFcsResult(String text) {
		// Receive XML and convert to Required format
		LOGGER.info("Message Received: {}", text);
	}
}
