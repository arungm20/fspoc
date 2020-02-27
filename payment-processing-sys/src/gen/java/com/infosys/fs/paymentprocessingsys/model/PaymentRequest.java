package com.infosys.fs.paymentprocessingsys.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.infosys.fs.paymentprocessingsys.model.PaymentDetails;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Payment Request to perform  Fraud check
 */
@ApiModel(description = "Payment Request to perform  Fraud check")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-02-25T11:40:31.704+05:30")

public class PaymentRequest   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("payer")
  private PaymentDetails payer = null;

  @JsonProperty("payee")
  private PaymentDetails payee = null;

  @JsonProperty("valueDate")
  private String valueDate = null;

  @JsonProperty("amount")
  private String amount = null;

  @JsonProperty("currency")
  private String currency = null;

  @JsonProperty("paymentInstruction")
  private String paymentInstruction = null;

  public PaymentRequest id(String id) {
    this.id = id;
    return this;
  }

  /**
   * transaction id in uuid format of the payment
   * @return id
  **/
  @ApiModelProperty(value = "transaction id in uuid format of the payment")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public PaymentRequest payer(PaymentDetails payer) {
    this.payer = payer;
    return this;
  }

  /**
   * Payment details of payer
   * @return payer
  **/
  @ApiModelProperty(value = "Payment details of payer")

  @Valid

  public PaymentDetails getPayer() {
    return payer;
  }

  public void setPayer(PaymentDetails payer) {
    this.payer = payer;
  }

  public PaymentRequest payee(PaymentDetails payee) {
    this.payee = payee;
    return this;
  }

  /**
   * Payment details of payee
   * @return payee
  **/
  @ApiModelProperty(value = "Payment details of payee")

  @Valid

  public PaymentDetails getPayee() {
    return payee;
  }

  public void setPayee(PaymentDetails payee) {
    this.payee = payee;
  }

  public PaymentRequest valueDate(String valueDate) {
    this.valueDate = valueDate;
    return this;
  }

  /**
   * Payment date on which transaction is performed in format YYYY-MM-DDThh:mm:ss for example : 2004-02-21T08:35:30 
   * @return valueDate
  **/
  @ApiModelProperty(value = "Payment date on which transaction is performed in format YYYY-MM-DDThh:mm:ss for example : 2004-02-21T08:35:30 ")


  public String getValueDate() {
    return valueDate;
  }

  public void setValueDate(String valueDate) {
    this.valueDate = valueDate;
  }

  public PaymentRequest amount(String amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Amount of the transaction
   * @return amount
  **/
  @ApiModelProperty(value = "Amount of the transaction")


  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public PaymentRequest currency(String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * Currency code
   * @return currency
  **/
  @ApiModelProperty(value = "Currency code")


  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public PaymentRequest paymentInstruction(String paymentInstruction) {
    this.paymentInstruction = paymentInstruction;
    return this;
  }

  /**
   * Payment Instruction Optional
   * @return paymentInstruction
  **/
  @ApiModelProperty(value = "Payment Instruction Optional")


  public String getPaymentInstruction() {
    return paymentInstruction;
  }

  public void setPaymentInstruction(String paymentInstruction) {
    this.paymentInstruction = paymentInstruction;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentRequest paymentRequest = (PaymentRequest) o;
    return Objects.equals(this.id, paymentRequest.id) &&
        Objects.equals(this.payer, paymentRequest.payer) &&
        Objects.equals(this.payee, paymentRequest.payee) &&
        Objects.equals(this.valueDate, paymentRequest.valueDate) &&
        Objects.equals(this.amount, paymentRequest.amount) &&
        Objects.equals(this.currency, paymentRequest.currency) &&
        Objects.equals(this.paymentInstruction, paymentRequest.paymentInstruction);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, payer, payee, valueDate, amount, currency, paymentInstruction);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentRequest {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    payer: ").append(toIndentedString(payer)).append("\n");
    sb.append("    payee: ").append(toIndentedString(payee)).append("\n");
    sb.append("    valueDate: ").append(toIndentedString(valueDate)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    paymentInstruction: ").append(toIndentedString(paymentInstruction)).append("\n");
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

