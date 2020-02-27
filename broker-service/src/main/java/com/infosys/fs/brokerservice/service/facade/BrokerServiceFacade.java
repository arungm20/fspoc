package com.infosys.fs.brokerservice.service.facade;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infosys.fs.brokerservice.config.CustomRestTemplate;
import com.infosys.fs.brokerservice.exception.BadRequestException;
import com.infosys.fs.brokerservice.exception.ExternalSystemUnavailableException;
import com.infosys.fs.brokerservice.exception.InternalServerErrorException;
import com.infosys.fs.brokerservice.exception.NotFoundException;
import com.infosys.fs.brokerservice.model.PaymentResponse;
import com.infosys.fs.brokerservice.util.APIResponseView;

@Service
public class BrokerServiceFacade {
	
	@Value("${pps.service.url}")
	private String ppsServiceUrl;
	
	@Autowired
	RestTemplate restTemplate;
	
	public void invoke(PaymentResponse paymentResponse) throws BadRequestException, NotFoundException,
			InternalServerErrorException, ExternalSystemUnavailableException {
		
		CustomRestTemplate.invokeRestAPI(ppsServiceUrl, HttpMethod.POST, APIResponseView.class, null, null,
				paymentResponse, Arrays.asList(MediaType.APPLICATION_JSON), restTemplate);
	}
	
}
