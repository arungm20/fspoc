package com.infosys.fs.paymentprocessingsys.handler;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.apache.camel.CamelContext;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.infosys.fs.paymentprocessingsys.dao.PaymentProcessDaoBean;
import com.infosys.fs.paymentprocessingsys.dao.PaymentRequestBackUp;
import com.infosys.fs.paymentprocessingsys.exception.BadRequestException;
import com.infosys.fs.paymentprocessingsys.exception.ExternalSystemUnavailableException;
import com.infosys.fs.paymentprocessingsys.exception.InternalServerErrorException;
import com.infosys.fs.paymentprocessingsys.exception.NotFoundException;
import com.infosys.fs.paymentprocessingsys.model.PaymentRequest;
import com.infosys.fs.paymentprocessingsys.model.PaymentResponse;
import com.infosys.fs.paymentprocessingsys.service.PaymentProcessService;
import com.infosys.fs.paymentprocessingsys.util.StatusCodes;
import com.infosys.fs.paymentprocessingsys.validation.RequestValidator;

@Service
public class PaymentProcessHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProcessHandler.class);
	
	@Autowired
	private PaymentProcessService paymentProcessService;
	
	@Autowired
	private PaymentProcessDaoBean paymentProcessDaoBean;
	
	@Autowired
	private CamelContext camelContext;
	
	private ProducerTemplate producerTemplate;
	
	@Value("${pps.response.max.retry}")
	private int maxRetryCount;
	
	@PostConstruct
	public void createProducerTemplate() {
		producerTemplate = camelContext.createProducerTemplate();
	}
	
	public void performNextStep(Message message, PaymentRequest paymentRequest, int retryCount) {
		
		try {
			RequestValidator.validatePaymentRequest(paymentRequest);
			paymentProcessService.process(paymentRequest);
		} catch (BadRequestException e) {
			
			producerTemplate.sendBody("{{pps.messaging.response.rejected.output.queue}}", RequestValidator
					.getRejectedPaymentResponse(paymentRequest.getId(), e.getStatusDescription(), e.getParameters()));
		} catch (NotFoundException | InternalServerErrorException | ExternalSystemUnavailableException ex) {
			LOGGER.error("Unable to send Request to BS : {}. Backing up for further processing for id: {}",
					ex.getStatusDescription(), paymentRequest.getId());
			handleRecoverableError(message, paymentRequest, ex.getStatusDescription(), ex.getParameters(), retryCount);
			
		}
		
	}
	
	public void retryPaymentProcessCheck(Message message, String id) {
		PaymentRequestBackUp paymentRequestBackUp = paymentProcessDaoBean.retrievePaymentRequestBackUp(id);
		if (paymentRequestBackUp == null) {
			producerTemplate.sendBody("{{pps.messaging.response.rejected.output.queue}}", RequestValidator
					.getRejectedPaymentResponse(id, StatusCodes.NO_DATA_FOUND.getStatusDescription(), null));
		} else {
			performNextStep(message, paymentRequestBackUp.getPaymentRequest(), paymentRequestBackUp.getRetryCount());
		}
	}
	
	public ResponseEntity<Object> retryPaymentProcessCheck(String id) {
		PaymentRequestBackUp paymentRequestBackUp = paymentProcessDaoBean.retrievePaymentRequestBackUp(id);
		if (paymentRequestBackUp == null) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		} else {
			performNextStep(null, paymentRequestBackUp.getPaymentRequest(), paymentRequestBackUp.getRetryCount());
		}
		return new ResponseEntity<Object>(HttpStatus.ACCEPTED);
	}
	
	private void handleRecoverableError(Message message, PaymentRequest paymentRequest, String description,
			String[] parameters, int retryCount) {
		LOGGER.info("Retry Count : {}", retryCount);
		retryCount++;
		LOGGER.info("Retry Count after increment: {}", retryCount);
		if (retryCount == maxRetryCount) {
			LOGGER.info("PPS : Exhausted Max Retry for Request : Id : {}", paymentRequest.getId());
			producerTemplate.sendBody("{{pps.messaging.response.rejected.output.queue}}",
					RequestValidator.getRejectedPaymentResponse(paymentRequest.getId(),
							StatusCodes.INTERNAL_SERVER_ERROR.getStatusDescription(), null));
		} else {
			paymentProcessDaoBean.savePaymentRequest(paymentRequest, retryCount);
			producerTemplate.sendBodyAndHeader("{{pps.messaging.response.rejected.output.queue}}",
					RequestValidator.getRetryPaymentResponse(paymentRequest.getId(), description, parameters),
					"x-retry-count", retryCount);
		}
	}
	
	/** From API **/
	/**
	 * Method to check the status and route to respective queue
	 * 
	 * @param paymentResponse
	 * @return
	 */
	public ResponseEntity<Object> processResponse(@Valid PaymentResponse paymentResponse) {
		
		Gson gson = new Gson();
		if (paymentResponse.getStatus().equals("REJECTED")) {
			producerTemplate.sendBody("{{pps.messaging.response.rejected.output.queue}}", gson.toJson(paymentResponse));
		} else {
			producerTemplate.sendBody("{{pps.messaging.response.approved.output.queue}}", gson.toJson(paymentResponse));
			
		}
		LOGGER.info("PPS : API Response Message is successfully sent to the output queue : {}", paymentResponse);
		return new ResponseEntity<Object>(HttpStatus.OK);
		
	}


	
}
