package com.ankur.ApiManager.controller;

import com.ankur.ApiManager.model.SchemaModel;
import com.ankur.ApiManager.service.SchemaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apis/{apiId}/schemas")
public class SchemaController {

    @Autowired
    private SchemaService schemaService;

    // Get all schemas for an API
    @GetMapping
    public List<JsonNode> getSchemas(@PathVariable String apiId, @RequestParam Map<String, String> params) {
        // You can implement filtering, sorting with params
        return schemaService.getSchemas(apiId);
    }

    // Get schema by ID
    @GetMapping("/{schemaId}")
    public ResponseEntity<JsonNode> getSchema(@PathVariable String apiId, @PathVariable String schemaId) {
    	JsonNode list = schemaService.getSchemaById(schemaId);
        return list != null ? ResponseEntity.ok(list) : ResponseEntity.notFound().build();
    }

    // Create schema
    @PostMapping
    public JsonNode createSchema(@PathVariable String apiId, @RequestBody JsonNode schema) throws JsonMappingException, JsonProcessingException, IOException {
        return schemaService.createSchema(apiId, schema);
    }

    // Update schema
    @PutMapping("/{schemaId}")
    public ResponseEntity<JsonNode> updateSchema(@PathVariable String apiId, @PathVariable String schemaId, @RequestBody JsonNode schema) throws IOException {
        try {
            JsonNode updated = schemaService.updateSchema(schemaId, schema);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
        	e.printStackTrace();
        	return ResponseEntity.notFound().build();
        }
    }

    // Delete schema
    @DeleteMapping("/{schemaId}")
    public ResponseEntity<Void> deleteSchema(@PathVariable String schemaId) {
    	Logger logger = LoggerFactory.getLogger(SchemaController.class);
    	logger.info("Deleting schema with ID: {}", schemaId);
        schemaService.deleteSchema(schemaId);
        return ResponseEntity.noContent().build();
    }

    // Validate schema, generate from JSON, generate example, clone, convert, usage, dependencies, search, export, import, etc.  
    // should be similarly exposed with @Post, @Get endpoints as needed.
    
    // Example: Export schema
    @GetMapping("/{schemaId}/export")
    public ResponseEntity<byte[]> exportSchema(@PathVariable String apiId, @PathVariable String schemaId,
                                               @RequestParam(defaultValue = "json") String format) {
        byte[] data = schemaService.exportSchema(schemaId, format);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=schema_" + schemaId + "." + format)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }

    // Example: Import schema
    @PostMapping("/import")
    public ResponseEntity<String> importSchema(@PathVariable String apiId, @RequestParam("file") MultipartFile file) {
        try {
            schemaService.importSchema(file, apiId);
            return ResponseEntity.ok("Schema imported");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Import failed");
        }
    }
}
