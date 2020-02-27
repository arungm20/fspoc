package com.infosys.fs.brokerservice.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.infosys.fs.brokerservice.dao.repository.PaymentResponseRepository;
import com.infosys.fs.brokerservice.model.PaymentResponse;

@Repository
public class BrokerServiceDaoBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BrokerServiceDaoBean.class);
	
	@Autowired
	private PaymentResponseRepository paymentResponseRepository;
	
	public String savePaymentStatusResponse(PaymentResponse paymentResponse) {
		
		try {
			PaymentResponseBackUp paymentResponseBackUp = new PaymentResponseBackUp();
			paymentResponseBackUp.setPaymentId(paymentResponse.getId());
			paymentResponseBackUp.setPaymentResponse(paymentResponse);
			PaymentResponseBackUp backup = paymentResponseRepository.insert(paymentResponseBackUp);
			return backup.getPaymentId();
		} catch (Exception ex) {
			LOGGER.error("Exception occured while backing up Payment Response for Id: {}", paymentResponse.getId());
		}
		return null;
	}
	
	public PaymentResponse retrievePaymentResponse(String paymentId) {
		
		PaymentResponseBackUp paymentResponseBackUp = paymentResponseRepository.findByPaymentId(paymentId);
		if (paymentResponseBackUp != null) {
			deleteFraudCheckResponse(paymentResponseBackUp);
			return paymentResponseBackUp.getPaymentResponse();
		}
		return null;
		
	}
	
	private void deleteFraudCheckResponse(PaymentResponseBackUp paymentResponseBackUp) {
		paymentResponseRepository.delete(paymentResponseBackUp);
	}
	
}
