/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.10).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */


import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.infosys.fs.paymentprocessingsys.model.PaymentResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-02-19T09:57:09.592+05:30")

@Api(value = "v1", description = "the v1 API")
@RequestMapping(value ="/sys/payment-process")
public interface V1Api {

    @ApiOperation(value = "API to Receive fraud check result from Fraud check system", nickname = "paymentProcessResponse", notes = "API receives the response from Fraud check System", tags={ "Payment Processing", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad Request") })
    @RequestMapping(value = "/v1/fraud-check-response",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Object> paymentProcessResponse(@ApiParam(value = "Represents  Payment Response with the status for fraud check" ,required=true )  @Valid @RequestBody PaymentResponse paymentResponse);

}
