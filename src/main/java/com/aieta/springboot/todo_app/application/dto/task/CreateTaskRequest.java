package com.aieta.springboot.todo_app.application.dto.task;

import com.aieta.springboot.todo_app.domain.model.task.Priority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateTaskRequest {

    @NotBlank(message = "El titulo no puede estar vacio.")
    @Size(min = 3, max = 120, message = "El titulo debe tener entre 3 y 120 caracteres.")
    private String title;

    @Size(max = 720, message = "La descripcion no puede exceder los 720 caracteres")
    private String description;

    @NotNull(message = "La prioridad de la tarea no puede estar vacia.")
    private Priority priority;

    private String categoryId;

    private boolean completed;

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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
