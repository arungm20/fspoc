package com.infosys.fs.brokerservice.util;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * APIResponseView
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-11-06T11:32:16.381+05:30")

public class APIResponseView implements IView {
	
	@JsonProperty("statusCode")
	private String statusCode = null;
	
	@JsonProperty("statusDescription")
	private String statusDescription = null;
	
	@JsonProperty("severity")
	private String severity = null;
	
	public APIResponseView statusCode(String statusCode) {
		this.statusCode = statusCode;
		return this;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public APIResponseView statusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
		return this;
	}
	
	/**
	 * Defines the status of the API response. Status Description provides more
	 * information on the status
	 * 
	 * @return statusDescription
	 **/
	
	public String getStatusDescription() {
		return statusDescription;
	}
	
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	
	public APIResponseView severity(String severity) {
		this.severity = severity;
		return this;
	}
	
	/**
	 * Defines the severity of the API response in case of error response.
	 * Possible values can be INFORMATION, WARNING, ERROR, CRITICAL
	 * 
	 * @return severity
	 **/
	
	public String getSeverity() {
		return severity;
	}
	
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	
}
