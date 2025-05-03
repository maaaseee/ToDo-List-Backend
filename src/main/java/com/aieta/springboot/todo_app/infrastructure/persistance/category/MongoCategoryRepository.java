package com.aieta.springboot.todo_app.infrastructure.persistance.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.aieta.springboot.todo_app.domain.model.task.Category;

public interface MongoCategoryRepository extends MongoRepository<Category, String> {

    List<Category> findAllByUserId(String userId);

    Optional<Category> findByUserIdAndId(String userId, String taskId);
}
