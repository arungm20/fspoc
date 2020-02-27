package com.infosys.fs.brokerservice.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PaymentResponse received from Broker service
 */
@ApiModel(description = "PaymentResponse received from Broker service")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-02-19T11:23:22.698+05:30")

public class PaymentResponse   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("statusMessage")
  private String statusMessage = null;

  @JsonProperty("responseTimeStamp")
  private String responseTimeStamp = null;

  public PaymentResponse id(String id) {
    this.id = id;
    return this;
  }

  /**
   * transaction id in uuid format of the payment received in request
   * @return id
  **/
  @ApiModelProperty(value = "transaction id in uuid format of the payment received in request")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public PaymentResponse status(String status) {
    this.status = status;
    return this;
  }

  /**
   * Fraud Check Result status
   * @return status
  **/
  @ApiModelProperty(value = "Fraud Check Result status")


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public PaymentResponse statusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
    return this;
  }

  /**
   * Message to consumer if status is rejected - Suspicious Payment and if approved - Nothing found, all ok
   * @return statusMessage
  **/
  @ApiModelProperty(value = "Message to consumer if status is rejected - Suspicious Payment and if approved - Nothing found, all ok")


  public String getStatusMessage() {
    return statusMessage;
  }

  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }

  public PaymentResponse responseTimeStamp(String responseTimeStamp) {
    this.responseTimeStamp = responseTimeStamp;
    return this;
  }

  /**
   * Response time when status check was performed in format YYYY-MM-DDThh:mm:ss for example : 2004-02-21T08:35:30 
   * @return responseTimeStamp
  **/
  @ApiModelProperty(value = "Response time when status check was performed in format YYYY-MM-DDThh:mm:ss for example : 2004-02-21T08:35:30 ")


  public String getResponseTimeStamp() {
    return responseTimeStamp;
  }

  public void setResponseTimeStamp(String responseTimeStamp) {
    this.responseTimeStamp = responseTimeStamp;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentResponse paymentResponse = (PaymentResponse) o;
    return Objects.equals(this.id, paymentResponse.id) &&
        Objects.equals(this.status, paymentResponse.status) &&
        Objects.equals(this.statusMessage, paymentResponse.statusMessage) &&
        Objects.equals(this.responseTimeStamp, paymentResponse.responseTimeStamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, status, statusMessage, responseTimeStamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentResponse {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    statusMessage: ").append(toIndentedString(statusMessage)).append("\n");
    sb.append("    responseTimeStamp: ").append(toIndentedString(responseTimeStamp)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

