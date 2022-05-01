package com.example.demo.controller;


import com.example.demo.utils.ApiResponse;
import com.example.demo.utils.EventLogScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

/**
 * @Author  Rakesh
 * @CreatedAt  26-04-2022
 * @apiNote Application Performance Rest API controller
 * @version 1.0.1
 * @since 1.0
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {

    @Autowired
    private EventLogScheduler taskScheduler;

    private final RestTemplate restTemplate;

    public ApiController(EventLogScheduler taskScheduler, RestTemplateBuilder restTemplateBuilder) {
        this.taskScheduler = taskScheduler;
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * REST service to accept GET endpoint.
     * @param id - Mandatory id needed for processing.
     * @param endpoint - Optional endpoint which will create a post request if present.
     * @param request - Http request received.
     * @return - Response entity with status code and message.
     */
    @GetMapping(value = {"/smaato/accept"})
    public ResponseEntity<?> acceptRequest(@RequestParam("id") Integer id, @RequestParam(required = false) String endpoint, WebRequest request)
    {
        ResponseEntity<?> result;
        try {
            if (id == null) {
                result = new ResponseEntity<>(new ApiResponse("400", "failed"), HttpStatus.BAD_REQUEST);
            } else {
                if(!taskScheduler.getIdList().contains(id)) {
                    taskScheduler.addToIdList(id);
                    taskScheduler.setTotalCount(taskScheduler.getTotalCount() + 1L);
                }
                result = new ResponseEntity<>(new ApiResponse("200", "ok"), HttpStatus.OK);
                /* Endpoint format should be /api/smaato */
                if(endpoint != null && !endpoint.isEmpty()) {
                    String reqUri = ((ServletWebRequest)request).getRequest().getRequestURI();
                    String reqUrl = ((ServletWebRequest)request).getRequest().getRequestURL().toString();
                    String baseUrl = reqUrl.replace(reqUri, "");
                    this.restTemplate.postForObject(baseUrl + endpoint, taskScheduler.getTotalCount(), ApiResponse.class);
                    return result;
                }
            }
        } catch (Exception e) {
            result = new ResponseEntity<>(new ApiResponse("500", "failed"), HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("Error occurred: " + e.getMessage());
        }
        return  result;
    }

    /**
     * REST service to accept POST endpoint.
     * @param count - Count of request made in 1min.
     * @return - Response entity with status code and message.
     */
    @PostMapping(value = "/smaato/*")
    public ResponseEntity<?> postRequest(@RequestBody Integer count) {
        ResponseEntity<?> result;
        try {
            result = new ResponseEntity<>(new ApiResponse("200", "ok"), HttpStatus.OK);
            log.info("Endpoint post method is called with count: " + count);
        } catch (Exception e) {
            result = new ResponseEntity<>(new ApiResponse("500", "failed"), HttpStatus.OK);
            log.error("Error occurred: " + e.getMessage());
        }
        return  result;
    }

}
