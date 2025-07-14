package com.ankur.ApiManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ankur.ApiManager.model.VersionsModel;
import com.ankur.ApiManager.repository.VersionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApiVersionService {

    private final VersionRepository versionRepository;

    @Autowired
    public ApiVersionService(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    // Get all versions for a specific API
    public List<VersionsModel> getApiVersions(String apiId) {
        return versionRepository.findByApiId(apiId);
    }

    // Create a new version
    public VersionsModel createApiVersion(String apiId, VersionsModel versionData) {
        versionData.setApiId(apiId);
        //set the id before saving, if necessary
        versionData.setId(UUID.randomUUID().toString()); // Uncomment if you want to generate a new ID
        return versionRepository.save(versionData);
    }

    // Update an existing version
    public Optional<VersionsModel> updateApiVersion(String versionId, VersionsModel updatedData) {
        return versionRepository.findById(versionId).map(existingVersion -> {
            existingVersion.setVersion(updatedData.getVersion());
            existingVersion.setDescription(updatedData.getDescription());
            existingVersion.setChangelog(updatedData.getChangelog());
            existingVersion.setCreatedAt(updatedData.getCreatedAt());
            existingVersion.setActive(updatedData.isActive());
            existingVersion.setDownloads(updatedData.getDownloads());
            // if apiId might change, handle that too
            return versionRepository.save(existingVersion);
        });
    }

    // Delete a version by ID
    public void deleteApiVersion(String versionId) {
        versionRepository.deleteById(versionId);
    }
}
