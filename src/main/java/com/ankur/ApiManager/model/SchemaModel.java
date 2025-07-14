package com.ankur.ApiManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.Map;

// Collection name 'Schemas' already defined
@Document(collection = "Schemas")
public class SchemaModel {

    @Id
    private String id;

    private String apiId; // associate with API
    private String name;
    private String content; // JSON or schema content
    private String format;  // e.g., 'json', 'yaml', etc.
    private Map<String, Object> metadata; // extra info: usage, dependencies, etc.
    private Instant createdAt;
    private Instant updatedAt;

    // New added fields based on the React state
    private String type = "object";        // default to 'object'
    private String description;
    private Map<String, Property> properties; // for schema properties
    private String[] required;              // required properties
    
    private String example; // store JSON string

	 // getter/setter
	 public String getExampleJson() { return example; }
	 public void setExampleJson(String exampleJson) { this.example = exampleJson; }
	

	public void setExample(String example) throws JsonProcessingException {
	    // Serialize the object to a simple JSON string
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.findAndRegisterModules(); // Ensure proper handling of Java 8 types, etc.
	    this.example = example;
	}
	
	public String getExample() throws JsonMappingException, JsonProcessingException {
	    // Deserialize the JSON string back to a generic Object
//	    ObjectMapper mapper = new ObjectMapper();
//	    mapper.findAndRegisterModules(); // Ensure proper handling of Java 8 types, etc.
//	    return mapper.readValue(this.example, String.class);
		return example;
	}

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    // New fields getters/setters:

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Property> getProperties() {
        return properties;
    }
    public void setProperties(Map<String, Property> properties) {
        this.properties = properties;
    }

    public String[] getRequired() {
        return required;
    }
    public void setRequired(String[] required) {
        this.required = required;
    }

//    public Object getExample() {
//        return example;
//    }
//    public void setExample(Object example) {
//        this.example = example;
//    }

    // Nested class for property items
    public static class Property {
        private String type;        // e.g., string, number, boolean, object, array
        private String format;      // date, email, uuid, etc.
        private String description;
        private Boolean required;   // whether this property is required
        private Integer minLength;  // for string
        private Integer maxLength;
        private Double minimum;     // for number/integer
        private Double maximum;
        private Object example;

        // Getters and setters

        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }

        public String getFormat() {
            return format;
        }
        public void setFormat(String format) {
            this.format = format;
        }

        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }

        public Boolean getRequired() {
            return required;
        }
        public void setRequired(Boolean required) {
            this.required = required;
        }

        public Integer getMinLength() {
            return minLength;
        }
        public void setMinLength(Integer minLength) {
            this.minLength = minLength;
        }

        public Integer getMaxLength() {
            return maxLength;
        }
        public void setMaxLength(Integer maxLength) {
            this.maxLength = maxLength;
        }

        public Double getMinimum() {
            return minimum;
        }
        public void setMinimum(Double minimum) {
            this.minimum = minimum;
        }

        public Double getMaximum() {
            return maximum;
        }
        public void setMaximum(Double maximum) {
            this.maximum = maximum;
        }

        public Object getExample() {
            return example;
        }
        public void setExample(Object example) {
            this.example = example;
        }
    }
}