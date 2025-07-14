package com.ankur.ApiManager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ankur.ApiManager.model.EnvironmentModel;
import com.ankur.ApiManager.service.EnvironmentService;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/environments")
public class EnvironmentController {

    private final EnvironmentService environmentService;

    @Autowired
    public EnvironmentController(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    // Get all environments
    @GetMapping
    public List<EnvironmentModel> getEnvironments(@RequestParam(required = false) String filter, @RequestParam(required = false) String sort) {
        // Your service method can process filters/sorting
    	Logger logger = LoggerFactory.getLogger(EnvironmentController.class);
    	logger.info("Fetching all environments with filter: {}, sort: {}", filter, sort);
        return environmentService.getAllEnvironments();
    }

    // Get environment by ID
    @GetMapping("/{id}")
    public ResponseEntity<EnvironmentModel> getEnvironment(@PathVariable String id) {
        return environmentService.getEnvironmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create environment
    @PostMapping
    public ResponseEntity<EnvironmentModel> createEnvironment(@RequestBody EnvironmentModel environment) {
        EnvironmentModel created = environmentService.saveEnvironment(environment);
		if (created == null) {
			return ResponseEntity.badRequest().build();
		}
		Logger logger = LoggerFactory.getLogger(EnvironmentController.class);
		logger.info("Environment created with ID: {}", created.getId());
        return ResponseEntity.ok(created);
    }

    // Update environment
    @PutMapping("/{id}")
    public ResponseEntity<EnvironmentModel> updateEnvironment(@PathVariable String id, @RequestBody EnvironmentModel environment) {
    	Logger logger = LoggerFactory.getLogger(EnvironmentController.class);
    	logger.info("Updating environment with ID: {}", id);
        return environmentService.getEnvironmentById(id)
                .map(existing -> {
                    existing.setName(environment.getName());
                    existing.setBaseUrl(environment.getBaseUrl());
                    existing.setVariables(environment.getVariables());
                    existing.setDescription(environment.getDescription());
                    existing.setActive(environment.getActive());
                    existing.setUpdatedAt(Instant.now());
                    EnvironmentModel updated = environmentService.saveEnvironment(existing);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete environment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvironment(@PathVariable String id) {
        environmentService.deleteEnvironment(id);
        Logger logger = LoggerFactory.getLogger(EnvironmentController.class);
        logger.info("Environment with ID: {} deleted", id);
        return ResponseEntity.noContent().build();
    }

    // Clone environment
    @PostMapping("/{id}/clone")
    public ResponseEntity<EnvironmentModel> cloneEnvironment(@PathVariable String id, @RequestBody CloneRequest request) {
        EnvironmentModel cloned = environmentService.cloneEnvironment(id, request.getName());
        return ResponseEntity.ok(cloned);
    }

    // Set active environment
    @PostMapping("/{id}/activate")
    public ResponseEntity<EnvironmentModel> setActive(@PathVariable String id) {
        EnvironmentModel env = environmentService.setActive(id);
        return ResponseEntity.ok(env);
    }

    // Test connection
    @PostMapping("/{id}/test")
    public ResponseEntity<String> testEnvironment(@PathVariable String id) {
        boolean success = environmentService.testConnection(id);
        String message = success ? "Connection successful" : "Connection failed";
        return ResponseEntity.ok(message);
    }

    // Get environment variables
    @GetMapping("/{id}/variables")
    public ResponseEntity<Map<String, String>> getVariables(@PathVariable String id) {
        return environmentService.getVariables(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update environment variables
    @PutMapping("/{id}/variables")
    public ResponseEntity<Map<String, String>> updateVariables(@PathVariable String id, @RequestBody Map<String, String> variables) {
        boolean updated = environmentService.updateVariables(id, variables);
        if (updated) {
            return getVariables(id);
        }
        return ResponseEntity.notFound().build();
    }

    // Export environment as JSON
    @GetMapping("/{id}/export")
    public ResponseEntity<byte[]> exportEnvironment(@PathVariable String id) {
        byte[] data = environmentService.exportEnvironment(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=environment_" + id + ".json")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }

    // Import environment from JSON file
    @PostMapping("/import")
    public ResponseEntity<String> importEnvironment(@RequestParam("file") MultipartFile file) {
        boolean success = environmentService.importEnvironment(file);
        return success ? ResponseEntity.ok("Import successful") : ResponseEntity.status(500).body("Import failed");
    }

    // DTO class for clone request
    public static class CloneRequest {
        private String name;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}