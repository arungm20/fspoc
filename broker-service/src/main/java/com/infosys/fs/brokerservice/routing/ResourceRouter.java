package com.infosys.fs.brokerservice.routing;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.infosys.fs.brokerservice.handler.BrokerServiceHandler;
import com.infosys.fs.brokerservice.model.PaymentRequest;

@Service
public class ResourceRouter {
	
	@Autowired
	private BrokerServiceHandler brokerServiceHandler;
	
	public ResponseEntity<Object> processBSRequest(PaymentRequest paymentRequest) throws Exception {
		return brokerServiceHandler.process(paymentRequest);
		
	}
	
	public ResponseEntity<Object> retrieveFraudCheckStatus(@NotNull @Valid Object id) {
		
		return brokerServiceHandler.getFraudCheckStatusResponse((String) id);
	}
}
