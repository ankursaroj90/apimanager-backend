package com.ankur.ApiManager.service;

import com.ankur.ApiManager.model.ApisModel;
import com.ankur.ApiManager.repository.ApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service public class ApiService {

@Autowired
private ApiRepository apiRepository;

public List<ApisModel> getAllApis() {
    return apiRepository.findAll();
}

public ApisModel createApi(ApisModel api) {
	// TODO Auto-generated method stub
	return apiRepository.save(api);
}

public ApisModel getApiByName(String name) {
	// TODO Auto-generated method stub
	return apiRepository.findByName(name).stream().findFirst().orElse(null);
}

public ApisModel getApiById(String id) {
	// TODO Auto-generated method stub
	return apiRepository.findById(id).orElse(null); // Return null if not found
}

public void deleteApi(String id) {
	apiRepository.deleteById(id);
}

public ApisModel updateApi(String id, ApisModel updatedApi) {
	// TODO Auto-generated method stub
	ApisModel existingApi = getApiById(id);
	if (existingApi != null) {
		existingApi.setName(updatedApi.getName());
		existingApi.setStatus(updatedApi.getStatus());
		existingApi.setDescription(updatedApi.getDescription());
		existingApi.setVersion(updatedApi.getVersion());
		existingApi.setBaseUrl(updatedApi.getBaseUrl());
		existingApi.setEndpoints(updatedApi.getEndpoints());
		existingApi.setSchemas(updatedApi.getSchemas());
		existingApi.setCreatedAt(updatedApi.getCreatedAt());
		existingApi.setUpdatedAt(updatedApi.getUpdatedAt());
		existingApi.setTags(updatedApi.getTags());
		return apiRepository.save(existingApi);
	}else {
		throw new RuntimeException("API not found with id: " + id);
	}
}
}