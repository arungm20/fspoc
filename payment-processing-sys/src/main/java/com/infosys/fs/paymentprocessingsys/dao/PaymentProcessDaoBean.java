package com.infosys.fs.paymentprocessingsys.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.infosys.fs.paymentprocessingsys.dao.repository.PaymentRequestRepository;
import com.infosys.fs.paymentprocessingsys.model.PaymentRequest;

@Repository
public class PaymentProcessDaoBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProcessDaoBean.class);
	
	@Autowired
	private PaymentRequestRepository paymentRequestRepository;
	
	public String savePaymentRequest(PaymentRequest paymentRequest, int retryCount) {
		
		try {
			PaymentRequestBackUp paymentRequestBackUp = new PaymentRequestBackUp();
			paymentRequestBackUp.setPaymentId(paymentRequest.getId());
			paymentRequestBackUp.setPaymentRequest(paymentRequest);
			paymentRequestBackUp.setRetryCount(retryCount);
			PaymentRequestBackUp backup = paymentRequestRepository.insert(paymentRequestBackUp);
			return backup.getPaymentId();
		} catch (Exception ex) {
			LOGGER.error("Exception occured while backing up Payment Response for Id: {}", paymentRequest.getId());
		}
		return null;
	}
	
	public PaymentRequestBackUp retrievePaymentRequestBackUp(String paymentId) {
		
		PaymentRequestBackUp paymentRequestBackUp = paymentRequestRepository.findByPaymentRequestId(paymentId);
		if (paymentRequestBackUp != null) {
			deleteFraudCheckRequest(paymentRequestBackUp);
			return paymentRequestBackUp;
		}
		return null;
		
	}
	
	private void deleteFraudCheckRequest(PaymentRequestBackUp paymentRequestBackUp) {
		paymentRequestRepository.delete(paymentRequestBackUp);
	}
	
}
