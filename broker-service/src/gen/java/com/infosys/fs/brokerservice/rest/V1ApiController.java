package com.infosys.fs.brokerservice.rest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.fs.brokerservice.model.PaymentRequest;
import com.infosys.fs.brokerservice.routing.ResourceRouter;

import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-02-19T11:23:22.698+05:30")

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

    public ResponseEntity<Object> brokerServiceRequest(@ApiParam(value = "Represents  Payment entity" ,required=true )  @Valid @RequestBody PaymentRequest payment) throws Exception {
        String accept = request.getHeader("Accept");
        return resourceRouter.processBSRequest(payment);
    }

    public ResponseEntity<Object> brokerServiceRequest(@NotNull @ApiParam(value = "Represents  transaction id for which status needs to be retrieved", required = true) @Valid @RequestParam(value = "id", required = true) Object id) {
        String accept = request.getHeader("Accept");
        return resourceRouter.retrieveFraudCheckStatus(id);
    }

}
