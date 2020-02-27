package com.infosys.fs.paymentprocessingsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.fs.paymentprocessingsys.exception.BadRequestException;
import com.infosys.fs.paymentprocessingsys.exception.ExternalSystemUnavailableException;
import com.infosys.fs.paymentprocessingsys.exception.InternalServerErrorException;
import com.infosys.fs.paymentprocessingsys.exception.NotFoundException;
import com.infosys.fs.paymentprocessingsys.model.PaymentRequest;
import com.infosys.fs.paymentprocessingsys.service.facade.PaymentProcessFacade;

@Service
public class PaymentProcessService {

	@Autowired
	private PaymentProcessFacade paymentProcessFacade;
	
	public void process(PaymentRequest paymentRequest) throws BadRequestException, NotFoundException, InternalServerErrorException, ExternalSystemUnavailableException {

		paymentProcessFacade.invoke(paymentRequest);
	}
	
}
