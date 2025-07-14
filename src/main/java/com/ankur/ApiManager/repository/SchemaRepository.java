package com.ankur.ApiManager.repository;

import com.ankur.ApiManager.model.SchemaModel;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SchemaRepository extends MongoRepository<SchemaModel, String> {
    List<SchemaModel> findByApiId(String apiId);
}
