package com.infosys.fs.brokerservice.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.infosys.fs.brokerservice.model.PaymentResponse;

@Document
public class PaymentResponseBackUp {
	
	@Id
	private String paymentId;
	
	public String getPaymentId() {
		return paymentId;
	}
	
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	private PaymentResponse paymentResponse;
	
	public PaymentResponse getPaymentResponse() {
		return paymentResponse;
	}
	
	public void setPaymentResponse(PaymentResponse paymentResponse) {
		this.paymentResponse = paymentResponse;
	}
	
}
