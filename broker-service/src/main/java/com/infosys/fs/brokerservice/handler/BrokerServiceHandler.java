package com.infosys.fs.brokerservice.handler;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.infosys.fs.brokerservice.dao.BrokerServiceDaoBean;
import com.infosys.fs.brokerservice.mapper.FraudCheckMapper;
import com.infosys.fs.brokerservice.model.PaymentRequest;
import com.infosys.fs.brokerservice.model.PaymentResponse;
import com.infosys.fs.brokerservice.service.BrokerService;
import com.infosys.fs.fraudchecksystem.jaxb.PaymentResponseXml;

@Service
public class BrokerServiceHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BrokerServiceHandler.class);
	
	@Autowired
	private CamelContext camelContext;
	
	@Autowired
	private BrokerService brokerService;
	
	@Autowired
	private BrokerServiceDaoBean brokerServiceDaoBean;
	
	private ProducerTemplate producerTemplate;
	@PostConstruct
	public void createProducerTemplate() {
		producerTemplate = camelContext.createProducerTemplate();
	}
	
	public ResponseEntity<Object> process(PaymentRequest paymentRequest) throws Exception {
		
		producerTemplate = camelContext.createProducerTemplate();
		producerTemplate.setDefaultEndpointUri("{{bs.request.output.queue}}");
		producerTemplate.sendBodyAndHeader("{{bs.request.output.queue}}", FraudCheckMapper.mapRequest(paymentRequest),
				"x-reply-to", "api");
		LOGGER.info("BS : Enqued Request to Output Queue");
		return new ResponseEntity<Object>(HttpStatus.ACCEPTED);
	}
	
	public void sendToPps(Object messageBody) throws JAXBException {
		
		PaymentResponseXml paymentResponseXml = FraudCheckMapper.getPaymentResponseXml(messageBody);
		PaymentResponse paymentResponse = FraudCheckMapper.mapPaymentResponse(paymentResponseXml);
		
		try {
			brokerService.process(paymentResponse);
		} catch (Exception e) {
			LOGGER.error("Unable to send Payment Response to PPS. Backing up");
			String id = brokerServiceDaoBean.savePaymentStatusResponse(paymentResponse);
			LOGGER.info("Backed up Payment Response for paymentId: {}", id);
			
		}
		
	}
	
	public ResponseEntity<Object> getFraudCheckStatusResponse(String id) {
		PaymentResponse paymentResponse = brokerServiceDaoBean.retrievePaymentResponse(id);
		
		if (paymentResponse != null) {
			return ResponseEntity.ok().body(paymentResponse);
		} else {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
	}
	
	
}
