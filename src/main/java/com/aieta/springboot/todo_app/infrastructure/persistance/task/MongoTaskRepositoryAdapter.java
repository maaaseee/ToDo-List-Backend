package com.aieta.springboot.todo_app.infrastructure.persistance.task;

import java.util.List;
import java.util.Optional;

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
    public List<Task> findAll(String userId) {
        return mongoTaskRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<Task> findById(String userId, String taskId) {
        return mongoTaskRepository.findByIdAndUserId(taskId, userId);
    }

    @Override
    public List<Task> findByCompleted(String userId, boolean completed) {
        return mongoTaskRepository.findAllByUserIdAndCompleted(userId, completed);
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
    public List<Task> findByPriority(Priority priority) {
        return mongoTaskRepository.findAllByPriority(priority);
    }
}
