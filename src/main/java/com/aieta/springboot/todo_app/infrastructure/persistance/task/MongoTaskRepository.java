package com.aieta.springboot.todo_app.infrastructure.persistance.task;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.aieta.springboot.todo_app.domain.model.task.Priority;
import com.aieta.springboot.todo_app.domain.model.task.Task;

import java.util.List;
import java.util.Optional;

public interface MongoTaskRepository extends MongoRepository<Task, String>  {

    List<Task> findAllByCompleted(boolean completed);
    List<Task> findAllByPriority(Priority priority);
    List<Task> findAllByUserId(String userId);

    Optional<Task> findByIdAndUserId(String id, String userId);
}
