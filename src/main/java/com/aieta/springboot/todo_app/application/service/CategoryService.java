package com.aieta.springboot.todo_app.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aieta.springboot.todo_app.application.dto.category.CategoryResponse;
import com.aieta.springboot.todo_app.application.dto.category.CreateCategoryRequest;
import com.aieta.springboot.todo_app.application.dto.category.UpdateCategoryRequest;
import com.aieta.springboot.todo_app.config.exception.CategoryNotFoundException;
import com.aieta.springboot.todo_app.config.mapper.CategoryMapper;
import com.aieta.springboot.todo_app.domain.model.task.Category;
import com.aieta.springboot.todo_app.domain.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public CategoryResponse createCategory(CreateCategoryRequest request) {
        Category newCategory = new Category(
            request.getName(), 
            request.getHexColor(), 
            request.getUserId()
        );

        return CategoryMapper.toResponse(repository.save(newCategory));
    }

    public CategoryResponse updateCategory(String userId, String categoryId, UpdateCategoryRequest request) {
        Category foundCategory = repository.findById(userId, categoryId)
                        .orElseThrow(() -> new CategoryNotFoundException("La categoria no existe en el sistema."));

        boolean wasModified = false;

        if (request.getName() != null && !request.getName().equals(foundCategory.getName())) {
            foundCategory.setName(request.getName());
            wasModified = true;
        }

        if (request.getHexColor() != null && !request.getHexColor().equals(foundCategory.getHexColor())) {
            foundCategory.setHexColor(request.getHexColor());
            wasModified = true;
        }

        if (!wasModified) {
            return CategoryMapper.toResponse(foundCategory);
        }

        return CategoryMapper.toResponse(repository.save(foundCategory));
    }

    public List<CategoryResponse> getAllCategories(String userId) {
        return repository.findAll(userId).stream()
                        .map(CategoryMapper::toResponse)
                        .toList();
    }

    public CategoryResponse getCategoryById(String userId, String categoryId) {
        Category foundCategory = repository.findById(userId, categoryId)
                        .orElseThrow(() -> new CategoryNotFoundException("La categoria no existe en el sistema."));

        return CategoryMapper.toResponse(foundCategory);
    }

    public void deleteCategory(String userId, String categoryId) {
        repository.findById(userId, categoryId)
                        .orElseThrow(() -> new CategoryNotFoundException("La categoria no existe en el sistema."));

        repository.deleteById(categoryId);
    }
}
