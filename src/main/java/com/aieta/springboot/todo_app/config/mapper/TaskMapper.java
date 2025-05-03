package com.aieta.springboot.todo_app.config.mapper;

import com.aieta.springboot.todo_app.application.dto.category.CategorySummary;
import com.aieta.springboot.todo_app.application.dto.task.TaskResponse;
import com.aieta.springboot.todo_app.domain.model.task.Category;
import com.aieta.springboot.todo_app.domain.model.task.Task;

public class TaskMapper {
    public static TaskResponse toResponse(Task task) {
        if (task == null) return null;

        Category category = task.getCategory();
        CategorySummary summary = null;
        if (category != null) {
            summary = new CategorySummary(
                category.getId(),
                category.getName(),
                category.getHexColor()
            );
        }

        return new TaskResponse(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            summary,
            task.getPriority(),
            task.isCompleted(),
            task.getUserId(),
            task.getCreatedAt(),
            task.getUpdatedAt()
        );
    }
}
