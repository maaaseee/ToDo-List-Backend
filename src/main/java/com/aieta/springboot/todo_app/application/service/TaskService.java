package com.aieta.springboot.todo_app.application.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.aieta.springboot.todo_app.application.dto.PagedResponse;
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
import com.aieta.springboot.todo_app.infrastructure.persistance.task.MongoTaskHelper;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;

    private final CategoryRepository categoryRepository;

    private final MongoTaskHelper taskHelper;

    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository, MongoTaskHelper taskHelper) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.taskHelper = taskHelper;
    }

    public PagedResponse<TaskResponse> getAllTasks(String userId, Pageable pageable, String titleSearch) {
        Page<Task> pagedTasks;

        if (StringUtils.hasText(titleSearch)) {
            pagedTasks = taskRepository.findAllByTitle(userId, titleSearch, pageable);
        } else {
            pagedTasks = taskRepository.findAll(userId, pageable);
        }

        return PagedResponse.fromPage(pagedTasks, TaskMapper::toResponse);
    }

    public TaskResponse getTaskById(String userId, String taskId) {
        Task foundTask = taskRepository.findById(userId, taskId)
                    .orElseThrow(() -> new TaskNotFoundException("La tarea no existe en el sistema."));

        return TaskMapper.toResponse(foundTask);
    }

    public PagedResponse<TaskResponse> getTasksByStatus(String userId, boolean completed, String titleSearch, Pageable pageable) {
        Page<Task> pagedTasks;

        if (StringUtils.hasText(titleSearch)) {
            pagedTasks = taskRepository.findByCompletedAndTitle(userId, completed, titleSearch, pageable);
        } else {
            pagedTasks = taskRepository.findByCompleted(userId, completed, pageable);
        }

        return PagedResponse.fromPage(pagedTasks, TaskMapper::toResponse);
    }

    public PagedResponse<TaskResponse> getTasksByPriority(String userId, Priority priority, String titleSearch, Pageable pageable) {
        Page<Task> pagedTasks;

        if (StringUtils.hasText(titleSearch)) {
            pagedTasks = taskRepository.findByPriorityAndTitle(userId, priority, titleSearch, pageable);
        } else {
            pagedTasks = taskRepository.findByPriority(userId, priority, pageable);
        }
        return PagedResponse.fromPage(pagedTasks, TaskMapper::toResponse);
    }

    public TaskResponse createTask(CreateTaskRequest request, String userId) {
        Category foundCategory = null;
        if (StringUtils.hasText(request.getCategoryId())) {
            foundCategory = categoryRepository.findById(userId, request.getCategoryId())
                        .orElseThrow(() -> new CategoryNotFoundException("La categoria no existe en el sistema."));
        }

        Task newTask = new Task(
            request.getTitle(), 
            request.getDescription(), 
            foundCategory, 
            request.getPriority(), 
            userId,
            request.isCompleted()
        );

        return TaskMapper.toResponse(taskRepository.save(newTask));
    }

    public TaskResponse updateTask(String taskId, UpdateTaskRequest request, String userId) {
        Task foundTask = taskRepository.findById(userId, taskId)
                        .orElseThrow(() -> new TaskNotFoundException("La tarea no existe en el sistema."));

        System.out.println();

        boolean wasModified = false;

        if (request.getTitle() != null && !request.getTitle().equals(foundTask.getTitle())) {
            foundTask.setTitle(request.getTitle());
            wasModified = true;
        }
    
        if (request.getDescription() != null && !request.getDescription().equals(foundTask.getDescription())) {
            foundTask.setDescription(request.getDescription());
            wasModified = true;
        }
    
        if (request.getPriority() != null && request.getPriority() != foundTask.getPriority()) {
            foundTask.setPriority(request.getPriority());
            wasModified = true;
        }
    
        if (request.getCompleted() != null && !request.getCompleted().equals(foundTask.isCompleted())) {
            foundTask.setCompleted(request.getCompleted());
            wasModified = true;
        }

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(userId, request.getCategoryId())
                            .orElseThrow(() -> new CategoryNotFoundException("La categoria no existe en el sistema."));

            if (foundTask.getCategory() == null || !foundTask.getCategory().getId().equals(category.getId())) {
                foundTask.setCategory(category);
                wasModified = true;
            }
        }

        if (!wasModified) {
            return TaskMapper.toResponse(foundTask);
        }

        foundTask.setUpdatedAt(LocalDateTime.now());

        return TaskMapper.toResponse(taskRepository.save(foundTask));
    }

    public TaskResponse markTaskAsCompleted(String userId, String taskId) {
        Task foundTask = taskRepository.findById(userId, taskId)
                            .orElseThrow(() -> new TaskNotFoundException("La tarea no existe en el sistema."));

        foundTask.setCompleted(!foundTask.isCompleted());
        foundTask.setUpdatedAt(LocalDateTime.now());

        return TaskMapper.toResponse(taskRepository.save(foundTask));
    }

    public void deleteTask(String userId, String taskId) {
        taskRepository.findById(userId, taskId)
                            .orElseThrow(() -> new TaskNotFoundException("La tarea no existe en el sistema."));

        taskRepository.deleteById(taskId);
    }

    public void removeCategoryFromTasks(String categoryId) {
        taskHelper.clearCategoryReference(categoryId);
    }
}

