package com.infosys.fs.paymentprocessingsys.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.infosys.fs.paymentprocessingsys.dao.PaymentRequestBackUp;

@Repository
public interface PaymentRequestRepository extends MongoRepository<PaymentRequestBackUp, String>{
	
	PaymentRequestBackUp findByPaymentRequestId(String paymentId);
	
}
