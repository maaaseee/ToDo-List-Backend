package com.aieta.springboot.todo_app.infrastructure.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aieta.springboot.todo_app.application.dto.category.CategoryResponse;
import com.aieta.springboot.todo_app.application.dto.category.CreateCategoryRequest;
import com.aieta.springboot.todo_app.application.dto.category.UpdateCategoryRequest;
import com.aieta.springboot.todo_app.application.service.CategoryService;
import com.aieta.springboot.todo_app.domain.model.user.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Categories", description = "Managment of categories")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Get all the categories from the system")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(categoryService.getAllCategories(currentUser.getId()));
    }

    @Operation(summary = "Get one category from the system")
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable String categoryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        User currentUser = (User) authentication.getPrincipal();

        return new ResponseEntity<>(categoryService.getCategoryById(currentUser.getId(), categoryId), HttpStatus.OK);
    }

    @Operation(summary = "Create one category on the system")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CreateCategoryRequest newCategory) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        User currentUser = (User) authentication.getPrincipal();

        return new ResponseEntity<>(categoryService.createCategory(newCategory, currentUser.getId()), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete one category from the system")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable String categoryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        User currentUser = (User) authentication.getPrincipal();

        categoryService.deleteCategory(currentUser.getId(), categoryId);
        return ResponseEntity.ok(Map.of("message", "Se borro la categoria correctamente"));
    }

    @Operation(summary = "Update one category from the system")
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(
        @PathVariable String categoryId, 
        @RequestBody @Valid UpdateCategoryRequest category) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        User currentUser = (User) authentication.getPrincipal();

        return new ResponseEntity<>(categoryService.updateCategory(currentUser.getId(), categoryId, category), HttpStatus.CREATED);
    } 
}