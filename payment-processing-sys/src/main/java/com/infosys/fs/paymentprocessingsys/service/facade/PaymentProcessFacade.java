package com.infosys.fs.paymentprocessingsys.service.facade;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infosys.fs.paymentprocessingsys.config.CustomRestTemplate;
import com.infosys.fs.paymentprocessingsys.exception.BadRequestException;
import com.infosys.fs.paymentprocessingsys.exception.ExternalSystemUnavailableException;
import com.infosys.fs.paymentprocessingsys.exception.InternalServerErrorException;
import com.infosys.fs.paymentprocessingsys.exception.NotFoundException;
import com.infosys.fs.paymentprocessingsys.model.PaymentRequest;
import com.infosys.fs.paymentprocessingsys.util.APIResponseView;

@Service
public class PaymentProcessFacade {
	
	@Value("${broker.service.url}")
	private String brokerServiceUrl;
	
	@Autowired
	RestTemplate restTemplate;
	
	public void invoke(PaymentRequest paymentRequest) throws BadRequestException, NotFoundException,
			InternalServerErrorException, ExternalSystemUnavailableException {
		
		CustomRestTemplate.invokeRestAPI(brokerServiceUrl, HttpMethod.POST, APIResponseView.class, null,
				null, paymentRequest, Arrays.asList(MediaType.APPLICATION_JSON), restTemplate);
	}
	
}
