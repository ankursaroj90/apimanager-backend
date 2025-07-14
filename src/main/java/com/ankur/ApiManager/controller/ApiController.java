package com.ankur.ApiManager.controller;

import com.ankur.ApiManager.model.ApisModel;
import com.ankur.ApiManager.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController @RequestMapping("/api")
public class ApiController {

@Autowired
private ApiService apiService;

@GetMapping("/all")
public List<ApisModel> getAllApis() {
	//print the log message using logger
	Logger logger = LoggerFactory.getLogger(ApiController.class);
	logger.info("Fetching all APIs");
    return apiService.getAllApis();
}

//fetch a specific API by name
@GetMapping("/find/{name}")
public ApisModel getApiByName(@PathVariable String name) {
	// print the log message using logger
	Logger logger = LoggerFactory.getLogger(ApiController.class);
	logger.info("Fetching API by name: {}", name);
	return apiService.getApiByName(name);
}

//fetch by ID
@GetMapping("/find/{id}")
public ApisModel getApiById(@PathVariable String id) {
	// print the log message using logger
	Logger logger = LoggerFactory.getLogger(ApiController.class);
	logger.info("Fetching API by ID: {}", id);
	return apiService.getApiById(id);
}

@PostMapping("/create")
public ApisModel createApi(@RequestBody ApisModel api) {
	//print the log message using logger
	Logger logger = LoggerFactory.getLogger(ApiController.class);
	logger.info("Creating API: {}", api.getName());
	return apiService.createApi(api);
}

//Update an existing API
@PutMapping("/update/{id}")
public ResponseEntity<ApisModel> updateApi(@PathVariable String id, @RequestBody ApisModel updatedApi) {
    // print the log message using logger
    Logger logger = LoggerFactory.getLogger(ApiController.class);
    logger.info("Updating API with ID: {}", id);
    
    //call the service to update the API
    ApisModel updated = apiService.updateApi(id, updatedApi);
	return ResponseEntity.ok(updated);
}

//delete an API by ID
@DeleteMapping("/delete/{id}")
public void deleteApi(@PathVariable String id) {
	// print the log message using logger
	Logger logger = LoggerFactory.getLogger(ApiController.class);
	logger.info("Deleting API with ID: {}", id);
	apiService.deleteApi(id);
}
}