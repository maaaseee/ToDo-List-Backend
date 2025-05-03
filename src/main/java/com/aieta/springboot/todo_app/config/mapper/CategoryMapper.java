package com.aieta.springboot.todo_app.config.mapper;

import com.aieta.springboot.todo_app.application.dto.category.CategoryResponse;
import com.aieta.springboot.todo_app.domain.model.task.Category;

public class CategoryMapper {

    public static CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
            category.getId(), 
            category.getName(), 
            category.getHexColor(), 
            category.getUserId()
        );
    }
}
