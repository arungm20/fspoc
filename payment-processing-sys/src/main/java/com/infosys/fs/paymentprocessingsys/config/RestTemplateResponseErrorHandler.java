package com.infosys.fs.paymentprocessingsys.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

/**
 * This class is used to handle error in restTemplate class
 *
 */
@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(RestTemplateResponseErrorHandler.class);
	
	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		LOG.error("handleError Response error: {} {}", response.getRawStatusCode(), response.getStatusText());
		// Method to handle the error returned by the Rest Template
		// The errors will be suppressed and those will be handled manually in
		// our code
	}
	
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		int statusCode = response.getRawStatusCode();
		LOG.debug("Response statusCode: {}", statusCode);
		// condition to check if the status of the response is of 2XX series.
		// If not, then this method needs to return true that its error
		// response.
		return (statusCode / 100) != 2;
	}
}
