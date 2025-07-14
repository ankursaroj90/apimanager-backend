package com.ankur.ApiManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Document(collection = "Endpoints")
public class EndpointsModel {

    @Id
    private String id;

    @Field("api_id")
    private String apiId;

    private String name;
    private String method;
    private String path;
    private String summary;
    private String description;
    private List<Parameter> parameters;
    private RequestBody requestBody;
    private Map<String, Response> responses;
    private List<String> tags;

    @Field("operation_id")
    private String operationId;

    private List<Security> security;

    // No-argument constructor
    public EndpointsModel() {}

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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public Map<String, Response> getResponses() {
        return responses;
    }

    public void setResponses(Map<String, Response> responses) {
        this.responses = responses;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public List<Security> getSecurity() {
        return security;
    }

    public void setSecurity(List<Security> security) {
        this.security = security;
    }

    // Nested static classes for complex fields

    public static class Parameter {
        private String id;
        private String name;
        private String in;
        private String type;
        private boolean required;
        private String description;

        public Parameter() {}

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIn() {
            return in;
        }

        public void setIn(String in) {
            this.in = in;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isRequired() {
            return required;
        }

        public void setRequired(boolean required) {
            this.required = required;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class RequestBody {
        private boolean required;
        private Map<String, Content> content;

        public RequestBody() {}

        public boolean isRequired() {
            return required;
        }

        public void setRequired(boolean required) {
            this.required = required;
        }

        public Map<String, Content> getContent() {
            return content;
        }

        public void setContent(Map<String, Content> content) {
            this.content = content;
        }

        public static class Content {
            private Schema schema;

            public Content() {}

            public Schema getSchema() {
                return schema;
            }

            public void setSchema(Schema schema) {
                this.schema = schema;
            }

            public static class Schema {
                private String type;

                public Schema() {}

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }
    }

    public static class Response {
        private String description;
        private Map<String, Content> content;

        public Response() {}

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Map<String, Content> getContent() {
            return content;
        }

        public void setContent(Map<String, Content> content) {
            this.content = content;
        }

        public static class Content {
            private Schema schema;

            public Content() {}

            public Schema getSchema() {
                return schema;
            }

            public void setSchema(Schema schema) {
                this.schema = schema;
            }

            public static class Schema {
                private String type;

                public Schema() {}

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }
    }

    public static class Security {
        private String type;
        private String name;

        public Security() {}

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
