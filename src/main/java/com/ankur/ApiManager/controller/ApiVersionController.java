package com.ankur.ApiManager.controller;

import com.ankur.ApiManager.model.VersionsModel;
import com.ankur.ApiManager.service.ApiVersionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/apis/{apiId}/versions")
public class ApiVersionController {

    private final ApiVersionService apiVersionService;

    public ApiVersionController(ApiVersionService apiVersionService) {
        this.apiVersionService = apiVersionService;
    }

    // Get all versions for a specific API
    @GetMapping
    public List<VersionsModel> getApiVersions(@PathVariable String apiId) {
    	Logger logger = LoggerFactory.getLogger(ApiVersionController.class);
    	logger.info("Fetching versions for API ID: {}", apiId);
        return apiVersionService.getApiVersions(apiId);
    }

    // Create a new API version
    @PostMapping
    public ResponseEntity<VersionsModel> createApiVersion(@PathVariable String apiId, @RequestBody VersionsModel versionData) {
    	Logger logger = LoggerFactory.getLogger(ApiVersionController.class);
    	logger.info("Creating new version for API ID: {}", apiId);
        VersionsModel createdVersion = apiVersionService.createApiVersion(apiId, versionData);
        return ResponseEntity.ok(createdVersion);
    }

    // Update an existing version
    @PutMapping("/{versionId}")
    public ResponseEntity<VersionsModel> updateApiVersion(
    		@PathVariable String versionId,
    		@PathVariable String apiId,
            @RequestBody VersionsModel updatedData) {
    	        Logger logger = LoggerFactory.getLogger(ApiVersionController.class);
    	        logger.info("Updating version ID: {} for API ID: {}", versionId, apiId);
        Optional<VersionsModel> updatedVersionOpt = apiVersionService.updateApiVersion(versionId, updatedData);
        return updatedVersionOpt
                .map(updatedVersion -> ResponseEntity.ok(updatedVersion))
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a version by ID
    @DeleteMapping("/{versionId}")
    public ResponseEntity<Void> deleteApiVersion(@PathVariable String versionId) {
    	Logger logger = LoggerFactory.getLogger(ApiVersionController.class);
    	logger.info("Deleting version ID: {}", versionId);
        apiVersionService.deleteApiVersion(versionId);
        return ResponseEntity.noContent().build();
    }
}
