package com.aieta.springboot.todo_app.domain.repository;

import java.util.List;
import java.util.Optional;

import com.aieta.springboot.todo_app.domain.model.task.Category;

public interface CategoryRepository {
    List<Category> findAll(String userId);
    Optional<Category> findById(String userId, String categoryId);
    Category save(Category newCategory);
    void deleteById(String categoryId);
}
