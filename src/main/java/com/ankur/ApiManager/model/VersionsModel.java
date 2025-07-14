package com.ankur.ApiManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "versions")
public class VersionsModel {

    @Id
    private String id;
    private String apiId; // Assuming this field is needed to link to the API

    private String version;
    private String description;
    private String changelog;
    private LocalDateTime createdAt;
    private boolean isActive;
    private int downloads;

    // Constructors
    public VersionsModel() { }

    public VersionsModel(String version, String description, String changelog,
                   LocalDateTime createdAt, boolean isActive, int downloads) {
        this.version = version;
        this.description = description;
        this.changelog = changelog;
        this.createdAt = createdAt;
        this.isActive = isActive;
        this.downloads = downloads;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
}
