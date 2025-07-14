package com.ankur.ApiManager.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ankur.ApiManager.model.VersionsModel;

@Repository
public interface VersionRepository extends MongoRepository<VersionsModel, String> {

	List<VersionsModel> findByApiId(String apiId);
    // You can define custom query methods here if needed
    // For example:
    // List<Version> findByIsActive(boolean isActive);
}
