package com.infosys.fs.paymentprocessingsys.routing;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.infosys.fs.paymentprocessingsys.handler.PaymentProcessHandler;
import com.infosys.fs.paymentprocessingsys.model.PaymentResponse;

@Service
public class ResourceRouter {
	
	@Autowired
	private PaymentProcessHandler paymentProcessHandler;
	
	/**
	 * Routing method to process payment status response
	 * 
	 * @param paymentResponse
	 * @return
	 */
	
	public ResponseEntity<Object> processPpsResponse(@Valid PaymentResponse paymentResponse) {
		
		return paymentProcessHandler.processResponse(paymentResponse);
	}

	/**
	 * Router method to retry Fraud check
	 * @param paymentResponse
	 * @return
	 */
	public ResponseEntity<Object> processRetryFraudCheck(@Valid PaymentResponse paymentResponse) {
		return paymentProcessHandler.retryPaymentProcessCheck(paymentResponse.getId());
	}
}
