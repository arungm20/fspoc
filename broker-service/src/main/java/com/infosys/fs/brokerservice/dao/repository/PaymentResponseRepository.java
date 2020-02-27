package com.infosys.fs.brokerservice.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.infosys.fs.brokerservice.dao.PaymentResponseBackUp;

@Repository
public interface PaymentResponseRepository extends MongoRepository<PaymentResponseBackUp, String>{
	
	PaymentResponseBackUp findByPaymentId(String paymentId);
	
}
