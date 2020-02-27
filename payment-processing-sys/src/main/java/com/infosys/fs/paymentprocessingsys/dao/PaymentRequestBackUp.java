package com.infosys.fs.paymentprocessingsys.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.infosys.fs.paymentprocessingsys.model.PaymentRequest;

@Document
public class PaymentRequestBackUp {
	
	@Id
	private String paymentId;
	
	private PaymentRequest paymentRequest;
	
	private int retryCount;
	
	public int getRetryCount() {
		return retryCount;
	}
	
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	
	public String getPaymentId() {
		return paymentId;
	}
	
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	public PaymentRequest getPaymentRequest() {
		return paymentRequest;
	}
	
	public void setPaymentRequest(PaymentRequest paymentRequest) {
		this.paymentRequest = paymentRequest;
	}
	
}
