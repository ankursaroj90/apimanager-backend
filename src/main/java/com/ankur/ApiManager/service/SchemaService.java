package com.ankur.ApiManager.service;

import com.ankur.ApiManager.model.SchemaModel;
import com.ankur.ApiManager.model.SchemaModel.Property;
import com.ankur.ApiManager.repository.SchemaRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SchemaService {

	@Autowired
	private SchemaRepository schemaRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	private ObjectMapper objectMapper = new ObjectMapper();

	final String COLLECTION_NAME = "Schemas";

	// Get all schemas globally
	public List<JsonNode> getAll() {
		List<org.bson.Document> documents = mongoTemplate.findAll(org.bson.Document.class, COLLECTION_NAME);

		return documents.stream().map(doc -> {
			try {
				return objectMapper.readTree(doc.toJson());
			} catch (Exception e) {
				throw new RuntimeException("Error converting document to JsonNode", e);
			}
		}).collect(Collectors.toList());
	}

	// Get all schemas for an API
	public List<JsonNode> getSchemas(String id) {
		Query query = new Query(Criteria.where("apiId").is(id));
		List<Document> documents = mongoTemplate.find(query, org.bson.Document.class, COLLECTION_NAME);

		return documents.stream().map(doc -> {
			try {
				return objectMapper.readTree(doc.toJson());
			} catch (Exception e) {
				throw new RuntimeException("Error converting document to JsonNode", e);
			}
		}).collect(Collectors.toList());
	}

	public JsonNode getSchemaById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		org.bson.Document document = mongoTemplate.findOne(query, org.bson.Document.class, COLLECTION_NAME);

		if (document != null) {
			try {
				return objectMapper.readTree(document.toJson());
			} catch (Exception e) {
				throw new RuntimeException("Error converting document to JsonNode", e);
			}
		}
		return null;
	}

	public JsonNode createSchema(String apiId, JsonNode schema)
			throws JsonMappingException, JsonProcessingException, IOException {
		System.out.println("Schema : " + schema);
		Document document = Document.parse(schema.toString());
		Document schemaDoc = mongoTemplate.save(document, "Schemas");
		return objectMapper.readTree(schemaDoc.toJson());
	}
	
	public JsonNode updateSchema(String id, JsonNode updatedDocument) {

	    try {

	        // Create query to find the document by ID

	        Query query = new Query(Criteria.where("id").is(id));

	        // Remove _id from the update document to avoid conflicts

	        ObjectNode updateNode = updatedDocument.deepCopy();

	        updateNode.remove("id");

	        // Convert to BSON Document

	        Document updateDoc = Document.parse(updateNode.toString());

	        // Create update operation

	        Update update = new Update();

	        updateDoc.forEach((key, value) -> update.set(key, value));

	        // Perform the update

	        UpdateResult result = mongoTemplate.updateFirst(query, update, COLLECTION_NAME);

	        if (result.getMatchedCount() == 0) {

	            throw new RuntimeException("Document with ID " + id + " not found");

	        }

	        // Fetch and return the updated document

	        Document updatedDoc = mongoTemplate.findOne(query, Document.class, COLLECTION_NAME);

	        return objectMapper.readTree(updatedDoc.toJson());

	    } catch (Exception e) {
	    	e.printStackTrace();

	        throw new RuntimeException("Error updating document", e);

	    }

	}
	 

	public boolean deleteSchema(String id) {
		try {
			Query query = new Query(Criteria.where("id").is(id));
			return mongoTemplate.remove(query, COLLECTION_NAME).getDeletedCount() > 0;
		} catch (Exception e) {
			throw new RuntimeException("Error deleting document", e);
		}
	}

	public byte[] exportSchema(String id, String format) {
//		Optional<SchemaModel> schemaOpt = schemaRepository.findById(id);
//		if (schemaOpt.isEmpty())
//			throw new RuntimeException("Schema not found");
//		SchemaModel schema = schemaOpt.get();
//		// You can implement format conversion if needed
//		return schema.getContent().getBytes();
		return null;
	}

	public SchemaModel importSchema(MultipartFile file, String apiId) {
//		try {
//			String content = new String(file.getBytes());
//			SchemaModel schema = new SchemaModel();
//			schema.setContent(content);
//			schema.setApiId(apiId);
//			schema.setFormat("json"); // default, or detect from file
//			schema.setCreatedAt(Instant.now());
//			schema.setUpdatedAt(Instant.now());
//			// optionally set name from filename or internal content
//			return schemaRepository.save(schema);
//		} catch (IOException e) {
//			throw new RuntimeException("Failed to import schema", e);
//		}
		return null;
	}
	// Implement promote, clone, validate, generate, convert, usage, dependencies,
	// search, and other operations similarly.
}
