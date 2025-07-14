package com.ankur.ApiManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankur.ApiManager.model.SchemaModel;
import com.ankur.ApiManager.service.SchemaService;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

@RestController
public class SchemaGlobalController {

    @Autowired
    private SchemaService schemaService;

    @GetMapping("/schemas")
    public List<JsonNode> getAllSchemas() {
        return schemaService.getAll(); // fetch all schemas from collection
    }
}
