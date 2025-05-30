package com.aieta.springboot.todo_app.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aieta.springboot.todo_app.domain.model.task.Priority;
import com.aieta.springboot.todo_app.domain.model.task.Task;

public interface TaskRepository {
    Optional<Task> findById(String userId, String taskId);
    Page<Task> findAll(String userId, Pageable pageable);
    Page<Task> findByCompleted(String userId, boolean completed, Pageable pageable);
    Page<Task> findByPriority(String userId, Priority priority, Pageable pageable);
    Page<Task> findAllByTitle(String userId, String titleSearch, Pageable pageable);
    Page<Task> findByCompletedAndTitle(String userId, boolean completed, String titleSearch, Pageable pageable);
    Page<Task> findByPriorityAndTitle(String userId, Priority priority, String titleSearch, Pageable pageable);
    Task save(Task task);
    void deleteById(String id);
}
