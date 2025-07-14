package com.ankur.ApiManager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ankur.ApiManager.model.EndpointsModel;
import com.ankur.ApiManager.service.EndpointsService;

import java.util.List;
import java.util.Map;

@RestController
public class EndpointsController {

    @Autowired
    private EndpointsService endpointService;
    
    // Endpoint to fetch all endpoint
    @GetMapping("/apis/endpoints/all")
	public ResponseEntity<List<EndpointsModel>> getAllEndpoints() {
		List<EndpointsModel> endpoints = endpointService.getAllEndpoints();
		Logger logger = LoggerFactory.getLogger(EndpointsController.class);
		logger.info("Fetching all endpoints");
		return ResponseEntity.ok(endpoints);
	}

    @GetMapping("/apis/{apiId}/endpoints")
public ResponseEntity<List<EndpointsModel>> getEndpoints(@PathVariable String apiId,
                                                    @RequestParam(required = false) String range) {
        List<EndpointsModel> endpoints = endpointService.getEndpointsByApiId(apiId);
        Logger logger = LoggerFactory.getLogger(EndpointsController.class);
        logger.info("Fetching endpoints for API ID: {}", apiId);
        return ResponseEntity.ok(endpoints);
    }

    @GetMapping("/apis/{apiId}/endpoints/{endpointId}")
    public ResponseEntity<EndpointsModel> getEndpoint(@PathVariable String apiId, @PathVariable String endpointId) {
    	EndpointsModel endpoint = endpointService.getEndpointById(endpointId);
        return ResponseEntity.ok(endpoint);
    }

    @PostMapping("/apis/{apiId}/endpoints")
    public ResponseEntity<EndpointsModel> createEndpoint(@PathVariable String apiId, @RequestBody EndpointsModel endpoint) {
    	EndpointsModel created = endpointService.createEndpoint(apiId, endpoint);
    	Logger logger = LoggerFactory.getLogger(EndpointsController.class);
    	logger.info("Creating endpoint for API ID: {}", apiId);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/apis/{apiId}/endpoints/{endpointId}")
    public ResponseEntity<EndpointsModel> updateEndpoint(@PathVariable String apiId,
                                                   @PathVariable String endpointId,
                                                   @RequestBody EndpointsModel endpoint) {
    	Logger logger = LoggerFactory.getLogger(EndpointsController.class);
    	logger.info("Updating endpoint with ID: {}", endpointId);
    	EndpointsModel updated = endpointService.updateEndpoint(endpointId, endpoint);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/apis/{apiId}/endpoints/{endpointId}")
    public ResponseEntity<Void> deleteEndpoint(@PathVariable String apiId, @PathVariable String endpointId) {
        endpointService.deleteEndpoint(endpointId);
        Logger logger = LoggerFactory.getLogger(EndpointsController.class);
        logger.info("Deleting endpoint with ID: {}", endpointId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/endpoints/{endpointId}/test")
    public ResponseEntity<Object> testEndpoint(@PathVariable String endpointId, @RequestBody Object requestData) {
        // Implement your logic to test the endpoint
        Object testResult = endpointService.testEndpoint(endpointId, requestData);
        return ResponseEntity.ok(testResult);
    }

    @PostMapping("/apis/{apiId}/endpoints/{endpointId}/mock")
    public ResponseEntity<Object> generateMock(@PathVariable String apiId, @PathVariable String endpointId) {
        Object mockResponse = endpointService.generateMock(endpointId);
        return ResponseEntity.ok(mockResponse);
    }

    @GetMapping("/apis/{apiId}/endpoints/{endpointId}/analytics")
    public ResponseEntity<Object> getAnalytics(@PathVariable String apiId, @PathVariable String endpointId,
                                                 @RequestParam String range) {
        Object analytics = endpointService.getAnalytics(endpointId, range);
        return ResponseEntity.ok(analytics);
    }

    @PostMapping("/apis/{apiId}/endpoints/{endpointId}/duplicate")
    public ResponseEntity<EndpointsModel> duplicateEndpoint(@PathVariable String apiId,
                                                      @PathVariable String endpointId,
                                                      @RequestBody Map<String, String> body) {
        String newName = body.get("name");
        EndpointsModel duplicated = endpointService.duplicateEndpoint(endpointId, newName);
        return ResponseEntity.ok(duplicated);
    }
}
