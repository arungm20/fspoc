package com.infosys.fs.paymentprocessingsys.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PaymentDetails of the payment 
 */
@ApiModel(description = "PaymentDetails of the payment ")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-02-25T11:40:31.704+05:30")

public class PaymentDetails   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("bank")
  private String bank = null;

  @JsonProperty("countryCode")
  private String countryCode = null;

  public PaymentDetails name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of payer or payee
   * @return name
  **/
  @ApiModelProperty(value = "Name of payer or payee")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PaymentDetails bank(String bank) {
    this.bank = bank;
    return this;
  }

  /**
   * Bank of payer or payee
   * @return bank
  **/
  @ApiModelProperty(value = "Bank of payer or payee")


  public String getBank() {
    return bank;
  }

  public void setBank(String bank) {
    this.bank = bank;
  }

  public PaymentDetails countryCode(String countryCode) {
    this.countryCode = countryCode;
    return this;
  }

  /**
   * CountryCode of payer or payee
   * @return countryCode
  **/
  @ApiModelProperty(value = "CountryCode of payer or payee")


  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentDetails paymentDetails = (PaymentDetails) o;
    return Objects.equals(this.name, paymentDetails.name) &&
        Objects.equals(this.bank, paymentDetails.bank) &&
        Objects.equals(this.countryCode, paymentDetails.countryCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, bank, countryCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentDetails {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    bank: ").append(toIndentedString(bank)).append("\n");
    sb.append("    countryCode: ").append(toIndentedString(countryCode)).append("\n");
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

