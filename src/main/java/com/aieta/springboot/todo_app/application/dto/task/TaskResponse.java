package com.aieta.springboot.todo_app.application.dto.task;

import java.time.LocalDateTime;

import com.aieta.springboot.todo_app.application.dto.category.CategorySummary;
import com.aieta.springboot.todo_app.domain.model.task.Priority;

public class TaskResponse {

    private String id;
    private String title;
    private String description;
    private CategorySummary category;
    private Priority priority;
    private boolean completed;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TaskResponse(String id, String title, String description, CategorySummary category, Priority priority,
            boolean completed, String userId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.completed = completed;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public CategorySummary getCategory() {
        return category;
    }
    public void setCategory(CategorySummary category) {
        this.category = category;
    }
    public Priority getPriority() {
        return priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
