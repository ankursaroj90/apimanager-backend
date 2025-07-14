package com.ankur.ApiManager.repository;

import com.ankur.ApiManager.model.EnvironmentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentRepository extends MongoRepository<EnvironmentModel, String> {
    // You can add custom query methods here if needed
}