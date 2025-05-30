package com.aieta.springboot.todo_app.infrastructure.persistance.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.aieta.springboot.todo_app.domain.model.task.Priority;
import com.aieta.springboot.todo_app.domain.model.task.Task;

import java.util.Optional;

public interface MongoTaskRepository extends MongoRepository<Task, String>  {

    Page<Task> findAllByUserId(String userId, Pageable pageable);
    Page<Task> findAllByUserIdAndTitleContains(String userId, String titleSearch, Pageable pageable);

    Page<Task> findAllByUserIdAndCompleted(String userId, boolean completed, Pageable pageable);
    Page<Task> findAllByUserIdAndCompletedAndTitleContains(String userId, boolean completed, String titleSearch, Pageable pageable);

    Page<Task> findAllByUserIdAndPriority(String userId, Priority priority, Pageable pageable);
    Page<Task> findAllByUserIdAndPriorityAndTitleContains(String userId, Priority priority, String titleSearch, Pageable pageable);

    Optional<Task> findByIdAndUserId(String id, String userId);
}
