package com.ankur.ApiManager.repository;

import com.ankur.ApiManager.model.ApisModel;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRepository extends MongoRepository <ApisModel, String> {
    // Custom query methods can be defined here if needed
    // For example, to find APIs by name:
     List<ApisModel> findByName(String name);
}