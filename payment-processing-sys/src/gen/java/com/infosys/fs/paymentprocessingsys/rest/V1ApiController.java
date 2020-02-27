package com.infosys.fs.paymentprocessingsys.rest;

import com.infosys.fs.paymentprocessingsys.model.PaymentResponse;
import com.infosys.fs.paymentprocessingsys.routing.ResourceRouter;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-02-25T11:40:31.704+05:30")

@Controller
public class V1ApiController implements V1Api {

    private static final Logger log = LoggerFactory.getLogger(V1ApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    @Autowired
    private ResourceRouter resourceRouter;

    @org.springframework.beans.factory.annotation.Autowired
    public V1ApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Object> paymentProcessResponse(@ApiParam(value = "Represents  Payment Response with the status for fraud check" ,required=true )  @Valid @RequestBody PaymentResponse paymentResponse) {
        String accept = request.getHeader("Accept");
        return resourceRouter.processPpsResponse(paymentResponse);
    }

    public ResponseEntity<Object> retryFraudCheck(@ApiParam(value = "Represents  payment id of the transaction to be retriggered. Only id needs to be sent to the API. other parameters will be ignored" ,required=true )  @Valid @RequestBody PaymentResponse paymentResponse) {
        String accept = request.getHeader("Accept");
        return resourceRouter.processRetryFraudCheck(paymentResponse);
    }

}
