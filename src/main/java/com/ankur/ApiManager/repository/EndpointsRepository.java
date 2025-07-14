package com.ankur.ApiManager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ankur.ApiManager.model.EndpointsModel;

import java.util.List;

public interface EndpointsRepository extends MongoRepository<EndpointsModel, String> {
    List<EndpointsModel> findByApiId(String apiId);
}
