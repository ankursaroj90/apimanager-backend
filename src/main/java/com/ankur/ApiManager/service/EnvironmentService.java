package com.ankur.ApiManager.service;

import com.ankur.ApiManager.model.EnvironmentModel;
import com.ankur.ApiManager.repository.EnvironmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EnvironmentService {

    // Assuming you have a repository for EnvironmentModel, e.g.,
    // private final EnvironmentRepository environmentRepository;

    // Replace this with your actual repository
    private final EnvironmentRepository environmentRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public EnvironmentService(EnvironmentRepository environmentRepository) {
        this.environmentRepository = environmentRepository;
        this.objectMapper = new ObjectMapper();
    }

    // Get all environments
    public List<EnvironmentModel> getAllEnvironments() {
        return environmentRepository.findAll();
    }

    // Get environment by ID
    public Optional<EnvironmentModel> getEnvironmentById(String id) {
        return environmentRepository.findById(id);
    }

    // Save environment
    public EnvironmentModel saveEnvironment(EnvironmentModel environment) {
        if (environment.getCreatedAt() == null) {
            environment.setCreatedAt(Instant.now());
        }
        environment.setUpdatedAt(Instant.now());
        return environmentRepository.save(environment);
    }

    // Delete environment
    public void deleteEnvironment(String id) {
        environmentRepository.deleteById(id);
    }

    // Clone environment
    public EnvironmentModel cloneEnvironment(String id, String newName) {
        Optional<EnvironmentModel> existingOpt = environmentRepository.findById(id);
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Environment not found");
        }
        EnvironmentModel existing = existingOpt.get();
        EnvironmentModel clone = new EnvironmentModel();

        clone.setName(newName);
        clone.setBaseUrl(existing.getBaseUrl());
        clone.setVariables(existing.getVariables());
        clone.setDescription(existing.getDescription());
        clone.setActive(false);
        clone.setCreatedAt(Instant.now());
        clone.setUpdatedAt(Instant.now());
        return environmentRepository.save(clone);
    }

    // Set environment as active (deactivate others)
    public EnvironmentModel setActive(String id) {
        List<EnvironmentModel> all = environmentRepository.findAll();
        for (EnvironmentModel env : all) {
            if (Boolean.TRUE.equals(env.getActive())) {
                env.setActive(false);
                environmentRepository.save(env);
            }
        }
        Optional<EnvironmentModel> envOpt = environmentRepository.findById(id);
        if (envOpt.isEmpty()) {
            throw new RuntimeException("Environment not found");
        }
        EnvironmentModel env = envOpt.get();
        env.setActive(true);
        return environmentRepository.save(env);
    }

    // Test connection (dummy implementation)
    public boolean testConnection(String id) {
        // Implement real network test based on environment details
        // For now, just return true
        return true;
    }

    // Get environment variables
    public Optional<Map<String, String>> getVariables(String id) {
        return environmentRepository.findById(id).map(EnvironmentModel::getVariables);
    }

    // Update environment variables
    public boolean updateVariables(String id, Map<String, String> variables) {
        Optional<EnvironmentModel> opt = environmentRepository.findById(id);
        if (opt.isEmpty()) return false;
        EnvironmentModel env = opt.get();
        env.setVariables(variables);
        env.setUpdatedAt(Instant.now());
        environmentRepository.save(env);
        return true;
    }

    // Export environment as JSON bytes
    public byte[] exportEnvironment(String id) {
        Optional<EnvironmentModel> envOpt = environmentRepository.findById(id);
        if (envOpt.isEmpty()) {
            throw new RuntimeException("Environment not found");
        }
        try {
            String json = objectMapper.writeValueAsString(envOpt.get());
            return json.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Error serializing environment", e);
        }
    }

    // Import environment from uploaded file
    public boolean importEnvironment(MultipartFile file) {
        try {
            byte[] data = file.getBytes();
            EnvironmentModel env = objectMapper.readValue(data, EnvironmentModel.class);
            env.setId(null); // create a new environment
            if (env.getCreatedAt() == null) {
                env.setCreatedAt(Instant.now());
            }
            env.setUpdatedAt(Instant.now());
            environmentRepository.save(env);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}