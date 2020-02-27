

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.fs.paymentprocessingsys.model.PaymentResponse;
import com.infosys.fs.paymentprocessingsys.routing.ResourceRouter;

import io.swagger.annotations.ApiParam;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-02-16T20:50:37.019+05:30")

@Controller
public class V1ApiController implements V1Api {

    private static final Logger log = LoggerFactory.getLogger(V1ApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public V1ApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }
    
    @Autowired
    private ResourceRouter resourceRouter;

    public ResponseEntity<Object> paymentProcessResponse(@ApiParam(value = "Represents  Payment Response with the status for fraud check" ,required=true )  @Valid @RequestBody PaymentResponse paymentResponse) {
        String accept = request.getHeader("Accept");
        return resourceRouter.processPpsResponse(paymentResponse);
    }

}
