package com.infosys.fs.brokerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.fs.brokerservice.model.PaymentResponse;
import com.infosys.fs.brokerservice.service.facade.BrokerServiceFacade;

@Service
public class BrokerService {

	@Autowired
	private BrokerServiceFacade brokerServiceFacade;
	
	public void process(PaymentResponse paymentResponse) throws Exception {
		brokerServiceFacade.invoke(paymentResponse);
	
	}
	
}
