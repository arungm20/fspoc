package com.infosys.fs.fcs.fraudchecksystem.service;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.infosys.fs.fraudchecksystem.jaxb.PaymentRequestXml;
import com.infosys.fs.fraudchecksystem.jaxb.PaymentResponseXml;

@Service
public class FCSService {
	
	private static final String REJECTED = "REJECTED";
	
	private static final String APPROVED = "APPROVED";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FCSService.class);
	
	@Value("#{'${fcs.blacklisted.names}'.split('\\|')}")
	private List<String> blackListedNameList;
	
	@Value("#{'${fcs.blacklisted.countries}'.split('\\|')}")
	private List<String> blackListedCountriesList;
	
	@Value("#{'${fcs.blacklisted.banks}'.split('\\|')}")
	private List<String> blackListedBankList;
	
	@Value("#{'${fcs.blacklisted.payment.instructions}'.split('\\|')}")
	private List<String> blackListedPaymentInstructions;
	
	@Value("${fcs.status.approved.message}")
	private String approvedStatusMessage;
	
	@Value("${fcs.status.rejected.message}")
	private String rejectedStatusMessage;
	
	@Value("${fcs.date.time.pattern}")
	private String dateTimePattern;
	
	private static JAXBContext jaxbRequestContext;
	
	private static JAXBContext jaxbResponseContext;
	static {
		try {
			jaxbRequestContext = JAXBContext.newInstance(PaymentRequestXml.class);
			jaxbResponseContext = JAXBContext.newInstance(PaymentResponseXml.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public String performFraudCheck(Object exchangeMessageBody) throws JAXBException {
		
		Unmarshaller jaxbUnmarshaller = jaxbRequestContext.createUnmarshaller();
		PaymentRequestXml paymentRequestXml = (PaymentRequestXml) jaxbUnmarshaller
				.unmarshal(new StringReader((String) exchangeMessageBody));
		
		PaymentResponseXml paymentResponseXml = evaluate(paymentRequestXml);
		
		Marshaller marshaller = jaxbResponseContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter sw = new StringWriter();
		marshaller.marshal(paymentResponseXml, sw);
		String xmlString = sw.toString();
		LOGGER.info("paymentResponseXml : {}", xmlString);
		return xmlString;
		
	}
	
	private PaymentResponseXml evaluate(PaymentRequestXml paymentRequestXml) {
		PaymentResponseXml paymentResponseXml = new PaymentResponseXml();
		paymentResponseXml.setId(paymentRequestXml.getId());
		if (isNotBlacklisted(paymentRequestXml.getPayer().getName(), blackListedNameList)
				&& isNotBlacklisted(paymentRequestXml.getPayee().getName(), blackListedNameList)
				&& isNotBlacklisted(paymentRequestXml.getPayer().getCountryCode(), blackListedCountriesList)
				&& isNotBlacklisted(paymentRequestXml.getPayee().getCountryCode(), blackListedCountriesList)
				&& isNotBlacklisted(paymentRequestXml.getPayer().getBank(), blackListedBankList)
				&& isNotBlacklisted(paymentRequestXml.getPayee().getBank(), blackListedBankList)
				&& isNotBlacklisted(paymentRequestXml.getPaymentInstruction(), blackListedPaymentInstructions)) {
			paymentResponseXml.setStatus(APPROVED);
			paymentResponseXml.setStatusMessage(approvedStatusMessage);
		} else {
			paymentResponseXml.setStatus(REJECTED);
			paymentResponseXml.setStatusMessage(rejectedStatusMessage);
		}
		paymentResponseXml.setResponseTimeStamp(getResponseTimeStamp());
		return paymentResponseXml;
	}

	private String getResponseTimeStamp() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
		String formatDateTime = now.format(formatter);
		return formatDateTime;
	}
	
	public static boolean isNotBlacklisted(String input, List<String> blackListedList) {
		
		LOGGER.info("Check  : {} in : {}", input, blackListedList);
		if (StringUtils.isBlank(input)) {
			return true;
		}
		return blackListedList.stream().parallel().noneMatch(input::contains);
	}
	
}
