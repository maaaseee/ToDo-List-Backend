package com.aieta.springboot.todo_app.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.aieta.springboot.todo_app.application.dto.task.CreateTaskRequest;
import com.aieta.springboot.todo_app.application.dto.task.TaskResponse;
import com.aieta.springboot.todo_app.config.exception.CategoryNotFoundException;
import com.aieta.springboot.todo_app.config.exception.TaskNotFoundException;
import com.aieta.springboot.todo_app.domain.model.task.Category;
import com.aieta.springboot.todo_app.domain.model.task.Priority;
import com.aieta.springboot.todo_app.domain.model.task.Task;
import com.aieta.springboot.todo_app.domain.model.user.User;
import com.aieta.springboot.todo_app.domain.repository.CategoryRepository;
import com.aieta.springboot.todo_app.domain.repository.TaskRepository;
import com.aieta.springboot.todo_app.domain.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private TaskService taskService;

    // ? Save task
    @Test
    void createTask_shouldReturnTaskResponse_whenValidInput() {
        // Arrange
        String userId = "123";
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("Test Tarea");
        request.setDescription("Desc");
        request.setPriority(Priority.MEDIUM);
        request.setCompleted(false);
        request.setCategoryId(null);

        User mockUser = new User("John Doe", "johnd03@hotmail.com", "1234abcd");
        mockUser.setId(userId);

        Task mockTask = new Task(
            request.getTitle(), 
            request.getDescription(), 
            null, 
            request.getPriority(), 
            mockUser.getId(), 
            request.isCompleted()
        );
        mockTask.setId("abc123");

        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);
        
        // Act
        TaskResponse response = taskService.createTask(request, userId);

        // Assert
        assertNotNull(response);
        assertEquals("abc123", response.getId());
        assertEquals("Test Tarea", response.getTitle());
    }

    @Test
    void createTask_shouldThrowException_whenCategoryNotFound() {
        // Arrange
        String userId = "u678";
        String missingCategoryId = "cat999";

        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("Test Tarea");
        request.setPriority(Priority.MEDIUM);
        request.setCategoryId(missingCategoryId);

        when(categoryRepository.findById(userId, missingCategoryId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(CategoryNotFoundException.class, () ->
            taskService.createTask(request, userId)
        );
    }

    // ? Get Task By Id
    @Test
    void getTaskById_shouldReturnTask_whenExistsAndUserOwnsIt() {
        // Arrange
        String taskId = "task123";
        String userId = "user456";

        Task task = new Task();
        task.setId(taskId);
        task.setUserId(userId);

        // Act
        when(taskRepository.findById(userId, taskId)).thenReturn(Optional.of(task));
        TaskResponse response = taskService.getTaskById(userId, taskId);

        // Assert
        assertEquals(taskId, response.getId());
        verify(taskRepository).findById(userId, taskId);
    }

    @Test
    void getTaskById_shouldThrowException_whenTaskNotFound() {
        when(taskRepository.findById("nonexistent", "inexistent")).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () ->
            taskService.getTaskById("nonexistent", "inexistent")
        );
    }
}
