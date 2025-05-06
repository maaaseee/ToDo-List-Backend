package com.aieta.springboot.todo_app.domain.repository;

import java.util.List;
import java.util.Optional;

import com.aieta.springboot.todo_app.domain.model.task.Priority;
import com.aieta.springboot.todo_app.domain.model.task.Task;

public interface TaskRepository {
    List<Task> findAll(String userId);
    Optional<Task> findById(String userId, String taskId);
    List<Task> findByCompleted(String userId, boolean completed);
    List<Task> findByPriority(Priority priority);
    Task save(Task task);
    void deleteById(String id);
}
