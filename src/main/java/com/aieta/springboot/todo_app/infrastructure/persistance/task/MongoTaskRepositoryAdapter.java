package com.aieta.springboot.todo_app.infrastructure.persistance.task;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.aieta.springboot.todo_app.domain.model.task.Priority;
import com.aieta.springboot.todo_app.domain.model.task.Task;
import com.aieta.springboot.todo_app.domain.repository.TaskRepository;

@Repository
public class MongoTaskRepositoryAdapter implements TaskRepository {

    private final MongoTaskRepository mongoTaskRepository;

    public MongoTaskRepositoryAdapter(MongoTaskRepository mongoTaskRepository) {
        this.mongoTaskRepository = mongoTaskRepository;
    }

    @Override
    public Page<Task> findAll(String userId, Pageable pageable) {
        return mongoTaskRepository.findAllByUserId(userId, pageable);
    }

    @Override
    public Optional<Task> findById(String userId, String taskId) {
        return mongoTaskRepository.findByIdAndUserId(taskId, userId);
    }

    @Override
    public Page<Task> findByCompleted(String userId, boolean completed, Pageable pageable) {
        return mongoTaskRepository.findAllByUserIdAndCompleted(userId, completed, pageable);
    }

    @Override
    public Task save(Task task) {
        return mongoTaskRepository.save(task);
    }

    @Override
    public void deleteById(String id) {
        mongoTaskRepository.deleteById(id);
    }

    @Override
    public Page<Task> findByPriority(String userId, Priority priority, Pageable pageable) {
        return mongoTaskRepository.findAllByUserIdAndPriority(userId, priority, pageable);
    }

    @Override
    public Page<Task> findAllByTitle(String userId, String titleSearch, Pageable pageable) {
        return mongoTaskRepository.findAllByUserIdAndTitleContains(userId, titleSearch, pageable);
    }

    @Override
    public Page<Task> findByCompletedAndTitle(String userId, boolean completed, String titleSearch, Pageable pageable) {
        return mongoTaskRepository.findAllByUserIdAndCompletedAndTitleContains(userId, completed, titleSearch, pageable);
    }

    @Override
    public Page<Task> findByPriorityAndTitle(String userId, Priority priority, String titleSearch, Pageable pageable) {
        return mongoTaskRepository.findAllByUserIdAndPriorityAndTitleContains(userId, priority, titleSearch, pageable);
    }
}
