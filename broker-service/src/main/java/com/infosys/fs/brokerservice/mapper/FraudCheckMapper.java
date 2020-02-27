package com.infosys.fs.brokerservice.mapper;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.infosys.fs.brokerservice.model.PaymentDetails;
import com.infosys.fs.brokerservice.model.PaymentRequest;
import com.infosys.fs.brokerservice.model.PaymentResponse;

import com.infosys.fs.fraudchecksystem.jaxb.PaymentDetailsXml;
import com.infosys.fs.fraudchecksystem.jaxb.PaymentRequestXml;
import com.infosys.fs.fraudchecksystem.jaxb.PaymentResponseXml;

public class FraudCheckMapper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FraudCheckMapper.class);
	
	private static JAXBContext jaxbRequestContext;
	
	private static JAXBContext jaxbResponseContext;
	static {
		try {
			jaxbRequestContext = JAXBContext.newInstance(PaymentRequestXml.class);
			jaxbResponseContext = JAXBContext.newInstance(PaymentResponseXml.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String mapRequest(PaymentRequest paymentRequest) throws JAXBException {
		// Map and return
		// Convert to XML
		
		PaymentRequestXml paymentRequestXml = mapPaymentRequestXml(paymentRequest);
		
		Marshaller marshaller = jaxbRequestContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter sw = new StringWriter();
		marshaller.marshal(paymentRequestXml, sw);
		String xmlString = sw.toString();
		LOGGER.info("paymentRequestXml : {}", xmlString);
		return xmlString;
	}
	
	public static String mapResponse(Object exchangeMessageBody) throws JAXBException {
		// Map and return
		// Convert JSON
		PaymentResponseXml paymentResponseXml = getPaymentResponseXml(exchangeMessageBody);
		
		LOGGER.info("paymentResponseXml : {}", paymentResponseXml);
		PaymentResponse paymentResponse = getPaymentResponse(paymentResponseXml);
		
		Gson gson = new Gson();
		return gson.toJson(paymentResponse);
	}

	public static PaymentResponse mapPaymentResponse(PaymentResponseXml paymentResponseXml) {
		return getPaymentResponse(paymentResponseXml);
	}
	
	private static PaymentResponse getPaymentResponse(PaymentResponseXml paymentResponseXml) {
		PaymentResponse paymentResponse = new PaymentResponse();
		
		paymentResponse.setId(paymentResponseXml.getId());
		paymentResponse.setStatus(paymentResponseXml.getStatus());
		paymentResponse.setStatusMessage(paymentResponseXml.getStatusMessage());
		paymentResponse.responseTimeStamp(paymentResponseXml.getResponseTimeStamp());
		return paymentResponse;
	}
	
	public static PaymentResponseXml getPaymentResponseXml(Object exchangeMessageBody) throws JAXBException {
		Unmarshaller jaxbUnmarshaller = jaxbResponseContext.createUnmarshaller();
		PaymentResponseXml paymentResponseXml = (PaymentResponseXml) jaxbUnmarshaller
				.unmarshal(new StringReader((String) exchangeMessageBody));
		return paymentResponseXml;
	}
	

	private static PaymentRequestXml mapPaymentRequestXml(PaymentRequest paymentRequest) {
		
		PaymentRequestXml paymentRequestXml = new PaymentRequestXml();
		
		paymentRequestXml.setId(paymentRequest.getId());
		paymentRequestXml.setPayer(getPaymentDetails(paymentRequest.getPayer()));
		paymentRequestXml.setPayee(getPaymentDetails(paymentRequest.getPayee()));
		paymentRequestXml.setValueDate(paymentRequest.getValueDate());
		paymentRequestXml.setAmount(paymentRequest.getAmount());
		paymentRequestXml.setCurrency(paymentRequest.getCurrency());
		paymentRequestXml.setPaymentInstruction(paymentRequest.getPaymentInstruction());
		return paymentRequestXml;
		
	}
	
	private static PaymentDetailsXml getPaymentDetails(PaymentDetails paymentDetails) {
		PaymentDetailsXml paymentDetailsXml = new PaymentDetailsXml();
		paymentDetailsXml.setName(paymentDetails.getName());
		paymentDetailsXml.setBank(paymentDetails.getBank());
		paymentDetailsXml.setCountryCode(paymentDetails.getCountryCode());
		return paymentDetailsXml;
	}
}
