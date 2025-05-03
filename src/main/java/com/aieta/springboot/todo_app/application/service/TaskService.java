package com.aieta.springboot.todo_app.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.aieta.springboot.todo_app.application.dto.task.CreateTaskRequest;
import com.aieta.springboot.todo_app.application.dto.task.TaskResponse;
import com.aieta.springboot.todo_app.application.dto.task.UpdateTaskRequest;
import com.aieta.springboot.todo_app.config.exception.CategoryNotFoundException;
import com.aieta.springboot.todo_app.config.exception.TaskNotFoundException;
import com.aieta.springboot.todo_app.config.mapper.TaskMapper;
import com.aieta.springboot.todo_app.domain.model.task.Category;
import com.aieta.springboot.todo_app.domain.model.task.Priority;
import com.aieta.springboot.todo_app.domain.model.task.Task;
import com.aieta.springboot.todo_app.domain.repository.CategoryRepository;
import com.aieta.springboot.todo_app.domain.repository.TaskRepository;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;

    private final CategoryRepository categoryRepository;

    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<TaskResponse> getAllTasks(String userId) {
        return taskRepository.findAll(userId).stream()
                            .map(TaskMapper::toResponse)
                            .toList();
    }

    public Optional<TaskResponse> getTaskById(String userId, String taskId) {
        return taskRepository.findById(userId, taskId).map(TaskMapper::toResponse);
    }

    public List<TaskResponse> getTasksByStatus(boolean completed) {
        return taskRepository.findByCompleted(completed).stream()
                            .map(TaskMapper::toResponse)
                            .toList();
    }

    public List<TaskResponse> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority).stream()
                            .map(TaskMapper::toResponse)
                            .toList();
    }

    public TaskResponse createTask(CreateTaskRequest request) {
        Category foundCategory = null;
        if (StringUtils.hasText(request.getCategoryId())) {
            foundCategory = categoryRepository.findById(request.getUserId(), request.getCategoryId())
                        .orElseThrow(() -> new CategoryNotFoundException("La categoría no existe en el sistema."));
        }

        Task newTask = new Task(
            request.getTitle(), 
            request.getDescription(), 
            foundCategory, 
            request.getPriority(), 
            request.getUserId(),
            request.isCompleted()
        );

        return TaskMapper.toResponse(taskRepository.save(newTask));
    }

    public TaskResponse updateTask(String taskId, UpdateTaskRequest request, String userId) {
        Task foundTask = taskRepository.findById(userId, taskId)
                        .orElseThrow(() -> new TaskNotFoundException("La tarea no existe en el sistema."));

        if (request.getTitle() != null) foundTask.setTitle(request.getTitle());
        if (request.getDescription() != null) foundTask.setDescription(request.getDescription());
        if (request.getPriority() != null) foundTask.setPriority(request.getPriority());
        if (request.getCompleted() != null) foundTask.setCompleted(request.getCompleted());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(userId, request.getCategoryId())
                            .orElseThrow(() -> new CategoryNotFoundException("La categoria no existe en el sistema."));

            foundTask.setCategory(category);
        }

        foundTask.setUpdatedAt(LocalDateTime.now());

        return TaskMapper.toResponse(taskRepository.save(foundTask));
    }

    public Optional<TaskResponse> markTaskAsCompleted(String userId, String taskId) {
        return taskRepository.findById(userId, taskId)
                .map(existingTask -> {
                    existingTask.setCompleted(true);
                    existingTask.setUpdatedAt(LocalDateTime.now());
                    return TaskMapper.toResponse(taskRepository.save(existingTask));
                });
    }

    public void deleteTask(String userId, String taskId) {
        taskRepository.findById(userId, taskId)
                            .orElseThrow(() -> new TaskNotFoundException("La tarea no existe en el sistema."));

        taskRepository.deleteById(taskId);
    }
}

