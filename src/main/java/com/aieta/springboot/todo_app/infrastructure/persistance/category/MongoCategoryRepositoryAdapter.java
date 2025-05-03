package com.aieta.springboot.todo_app.infrastructure.persistance.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.aieta.springboot.todo_app.domain.model.task.Category;
import com.aieta.springboot.todo_app.domain.repository.CategoryRepository;

@Repository
public class MongoCategoryRepositoryAdapter implements CategoryRepository {

    private final MongoCategoryRepository mongoCategoryRepository;

    public MongoCategoryRepositoryAdapter(MongoCategoryRepository mongoCategoryRepository) {
        this.mongoCategoryRepository = mongoCategoryRepository;
    }

    @Override
    public List<Category> findAll(String userId) {
        return mongoCategoryRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<Category> findById(String userId, String categoryId) {
        return mongoCategoryRepository.findByUserIdAndId(userId, categoryId);
    }

    @Override
    public Category save(Category category) {
        return mongoCategoryRepository.save(category);
    }

    @Override
    public void deleteById(String categoryId) {
        mongoCategoryRepository.deleteById(categoryId);
    }
}
