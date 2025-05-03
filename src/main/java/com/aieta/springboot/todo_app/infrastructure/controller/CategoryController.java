package com.aieta.springboot.todo_app.infrastructure.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aieta.springboot.todo_app.application.dto.category.CategoryResponse;
import com.aieta.springboot.todo_app.application.dto.category.CreateCategoryRequest;
import com.aieta.springboot.todo_app.application.dto.category.UpdateCategoryRequest;
import com.aieta.springboot.todo_app.application.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Categories", description = "Managment of categories")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all the categories from the system")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(@RequestParam String userId) {
        return ResponseEntity.ok(categoryService.getAllCategories(userId));
    }

    @Operation(summary = "Get one category from the system")
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@RequestParam String userId, @PathVariable String categoryId) {
        return categoryService.getCategoryById(userId, categoryId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create one category on the system")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CreateCategoryRequest newCategory) {
        return new ResponseEntity<>(categoryService.createCategory(newCategory), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete one category from the system")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@RequestParam String userId, @PathVariable String categoryId) {
        categoryService.deleteCategory(userId, categoryId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update one category from the system")
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(
        @RequestParam String userId, 
        @PathVariable String categoryId, 
        @RequestBody @Valid UpdateCategoryRequest category) {

        return new ResponseEntity<>(categoryService.updateCategory(userId, categoryId, category), HttpStatus.CREATED);
    } 
}