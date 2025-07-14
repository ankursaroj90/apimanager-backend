package com.ankur.ApiManager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ankur.ApiManager.model.EndpointsModel;
import com.ankur.ApiManager.repository.EndpointsRepository;

import java.util.List;

@Service
public class EndpointsService {

    // Assuming you're using Spring Data MongoDB Repository
    @Autowired
    private EndpointsRepository endpointRepository;

    public List<EndpointsModel> getEndpointsByApiId(String apiId) {
        return endpointRepository.findByApiId(apiId);
    }

    public EndpointsModel getEndpointById(String id) {
        return endpointRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public EndpointsModel createEndpoint(String apiId, EndpointsModel endpoint) {
        endpoint.setApiId(apiId);
        return endpointRepository.save(endpoint);
    }

    public EndpointsModel updateEndpoint(String id, EndpointsModel endpoint) {
    	EndpointsModel existing = getEndpointById(id);
        // update fields
        existing.setName(endpoint.getName());
        existing.setPath(endpoint.getPath());
        existing.setSummary(endpoint.getSummary());
        existing.setMethod(endpoint.getMethod());
        existing.setDescription(endpoint.getDescription());
        existing.setResponses(endpoint.getResponses());
        existing.setParameters(endpoint.getParameters());
        existing.setTags(endpoint.getTags());
        existing.setSecurity(endpoint.getSecurity());
        existing.setRequestBody(endpoint.getRequestBody());
        Logger logger = LoggerFactory.getLogger(EndpointsService.class);
        logger.info("Updated endpoint with ID: {}", id);
        return endpointRepository.save(existing);
    }

    public void deleteEndpoint(String id) {
        endpointRepository.deleteById(id);
    }

    public Object testEndpoint(String endpointId, Object requestData) {
        // Implement testing logic, e.g., invoke the real API, mock response, etc.
        return "Mock test response";
    }

    public Object generateMock(String endpointId) {
        // Generate or return mock response
        return "Mock response data";
    }

    public Object getAnalytics(String endpointId, String range) {
        // Fetch analytics data, e.g., from logs or analytics system
        return "Analytics data for range: " + range;
    }

    public EndpointsModel duplicateEndpoint(String endpointId, String newName) {
    	EndpointsModel original = getEndpointById(endpointId);
    	EndpointsModel copy = new EndpointsModel();
        // Copy properties
        copy.setName(newName);
        copy.setPath(original.getPath());
        // etc.
        copy.setApiId(original.getApiId());
        return endpointRepository.save(copy);
    }

	public List<EndpointsModel> getAllEndpoints() {
		// TODO Auto-generated method stub
		return endpointRepository.findAll();
	}
}
