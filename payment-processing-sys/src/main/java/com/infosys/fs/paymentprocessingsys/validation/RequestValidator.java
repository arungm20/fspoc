package com.infosys.fs.paymentprocessingsys.validation;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.infosys.fs.paymentprocessingsys.exception.APIException.Severity;
import com.infosys.fs.paymentprocessingsys.exception.BadRequestException;
import com.infosys.fs.paymentprocessingsys.model.PaymentRequest;
import com.infosys.fs.paymentprocessingsys.model.PaymentResponse;
import com.infosys.fs.paymentprocessingsys.util.StatusCodes;

public class RequestValidator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestValidator.class);
	
	private RequestValidator() {
		
	}
	
	public static void validatePaymentRequest(Exchange exchange) {
		
		Gson gson = new Gson();
		PaymentRequest paymentRequest = gson.fromJson((String) exchange.getMessage().getBody(), PaymentRequest.class);
		try {
			validatePaymentRequest(paymentRequest);
		} catch (BadRequestException e) {
			exchange.getMessage().setBody(
					getRejectedPaymentResponse(paymentRequest.getId(), e.getStatusDescription(), e.getParameters()));
		}
	}
	
	public static String getRejectedPaymentResponse(String id, String description, String[] parameters) {
		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setId(id);
		paymentResponse.setStatus("REJECTED");
		paymentResponse.setStatusMessage(formatErrorMessage(description, parameters));
		paymentResponse.responseTimeStamp(getResponseTimeStamp());
		return new Gson().toJson(paymentResponse);
	}
	
	public static String getRetryPaymentResponse(String id, String description, String[] parameters) {
		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setId(id);
		paymentResponse.setStatus("RETRY");
		paymentResponse.setStatusMessage(formatErrorMessage(description, parameters));
		paymentResponse.responseTimeStamp(getResponseTimeStamp());
		return new Gson().toJson(paymentResponse);
	}
	
	private static String getResponseTimeStamp() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		String formatDateTime = now.format(formatter);
		return formatDateTime;
	}
	
	public static void validatePaymentRequest(PaymentRequest paymentRequest) throws BadRequestException {
		
		String missingAttribute = "";
		if (StringUtils.isBlank(paymentRequest.getId())) {
			missingAttribute = "id";
		} else if (paymentRequest.getPayer() == null) {
			missingAttribute = "payer";
		} else if (paymentRequest.getPayee() == null) {
			missingAttribute = "payee";
		} else if (StringUtils.isAnyBlank(paymentRequest.getPayer().getName())) {
			missingAttribute = "payerName";
		} else if (StringUtils.isBlank(paymentRequest.getPayer().getCountryCode())) {
			missingAttribute = "payerCountryCode";
		} else if (StringUtils.isBlank(paymentRequest.getPayer().getBank())) {
			missingAttribute = "payerBank";
		} else if (StringUtils.isBlank(paymentRequest.getPayee().getCountryCode())) {
			missingAttribute = "payeeCountryCode";
		} else if (StringUtils.isBlank(paymentRequest.getPayee().getBank())) {
			missingAttribute = "payeeBank";
		} else if (StringUtils.isBlank(paymentRequest.getPayee().getName())) {
			missingAttribute = "payeeName";
		} else if (StringUtils.isBlank(paymentRequest.getValueDate())) {
			missingAttribute = "valueDate";
		} else if (StringUtils.isBlank(paymentRequest.getAmount())) {
			missingAttribute = "amount";
		} else if (StringUtils.isBlank(paymentRequest.getCurrency())) {
			missingAttribute = "currency";
		}
		
		if (StringUtils.isNotBlank(missingAttribute)) {
			
			throw new BadRequestException(StatusCodes.MISSING_MANDATORY_PARAMETER.getStatusCode(),
					StatusCodes.MISSING_MANDATORY_PARAMETER.getStatusDescription(), Severity.ERROR, missingAttribute);
			
		}
		
		LOGGER.info("Valid Payment Request");
	}
	
	private static String formatErrorMessage(String statusDescription, String... parameters) {
		
		String errorMessage = null;
		if (StringUtils.isBlank(statusDescription)) {
			errorMessage = "";
		} else {
			errorMessage = MessageFormat.format(statusDescription, parameters);
		}
		LOGGER.error("Unable to send request to BS due to - {}", errorMessage);
		return errorMessage;
	}
}
