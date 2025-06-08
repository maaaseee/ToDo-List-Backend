package com.aieta.springboot.todo_app.infrastructure.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aieta.springboot.todo_app.application.dto.PagedResponse;
import com.aieta.springboot.todo_app.application.dto.task.CreateTaskRequest;
import com.aieta.springboot.todo_app.application.dto.task.TaskResponse;
import com.aieta.springboot.todo_app.application.dto.task.UpdateTaskRequest;
import com.aieta.springboot.todo_app.application.service.TaskService;
import com.aieta.springboot.todo_app.domain.model.task.Priority;
import com.aieta.springboot.todo_app.domain.model.user.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Tasks", description = "Managment of tasks for principal function of the page")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(
        summary = "List all tasks from the system", 
        description = "Returns an array of tasks that are saved on the system and are property of the user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", 
        content = {
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class))
            )
        }),
        @ApiResponse(responseCode = "404", description = "Resource not found", 
        content = {
            @Content(mediaType = "application/json")
        }),
        @ApiResponse(responseCode = "405", description = "Method not allowed/Invalid parameters", 
        content = {
            @Content(mediaType = "application/json")
        })
    })
    @GetMapping
    public ResponseEntity<PagedResponse<TaskResponse>> getAllTasks(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        User currentUser = (User) authentication.getPrincipal();

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return ResponseEntity.ok(taskService.getAllTasks(currentUser.getId(), pageable, title));
    }

    @Operation(summary = "Get one task from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", 
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = TaskResponse.class)
            )
        }),
        @ApiResponse(responseCode = "404", description = "Resource not found", 
        content = {
            @Content(mediaType = "application/json")
        }),
        @ApiResponse(responseCode = "405", description = "Method not allowed/Invalid parameters", 
        content = {
            @Content(mediaType = "application/json")
        })
    })
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(
            @Parameter(name = "taskId", description = "ID from the Task", required = true)
            @PathVariable String taskId
        ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(taskService.getTaskById(currentUser.getId(), taskId));
    }

    @Operation(summary = "List all tasks from the system depending on their status (completed or incompleted)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", 
        content = {
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class))
            )
        }),
        @ApiResponse(responseCode = "404", description = "Resource not found", 
        content = {
            @Content(mediaType = "application/json")
        }),
        @ApiResponse(responseCode = "405", description = "Method not allowed/Invalid parameters", 
        content = {
            @Content(mediaType = "application/json")
        })
    })
    @GetMapping("/status")
    public ResponseEntity<PagedResponse<TaskResponse>> getTasksByStatus(
            @Parameter(name = "completed", description = "State of the task, completed or not", required = true)
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "false") boolean completed,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        User currentUser = (User) authentication.getPrincipal();

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return ResponseEntity.ok(taskService.getTasksByStatus(currentUser.getId(), completed, title, pageable));
    }

    @GetMapping("/priority")
    public ResponseEntity<PagedResponse<TaskResponse>> getTasksByPriority(
            @RequestParam(required = false) String title,
            @RequestParam Priority priority,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        User currentUser = (User) authentication.getPrincipal();

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return ResponseEntity.ok(taskService.getTasksByPriority(currentUser.getId(), priority, title, pageable));
    }
    

    @Operation(summary = "Create one task on the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", 
        content = {
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskResponse.class)
                )
            }),
            @ApiResponse(responseCode = "404", description = "Resource not found", 
            content = {
                @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "405", description = "Method not allowed/Invalid parameters", 
            content = {
                @Content(mediaType = "application/json")
            })
        })
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid CreateTaskRequest task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        User currentUser = (User) authentication.getPrincipal();

        return new ResponseEntity<>(taskService.createTask(task, currentUser.getId()), HttpStatus.CREATED);
    }

    @Operation(summary = "Update one task from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", 
        content = {
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskResponse.class)
                )
            }),
            @ApiResponse(responseCode = "404", description = "Resource not found", 
            content = {
                @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "405", description = "Method not allowed/Invalid parameters", 
            content = {
                @Content(mediaType = "application/json")
            })
        })
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable String taskId, 
            @RequestBody @Valid UpdateTaskRequest updateTask
        ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        User currentUser = (User) authentication.getPrincipal();
        
        return new ResponseEntity<>(taskService.updateTask(taskId, updateTask, currentUser.getId()), HttpStatus.OK);
    }

    @Operation(summary = "Change status of a task on the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", 
        content = {
            @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskResponse.class)
                )
            }),
            @ApiResponse(responseCode = "404", description = "Resource not found", 
            content = {
                @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "405", description = "Method not allowed/Invalid parameters", 
            content = {
                @Content(mediaType = "application/json")
            })
        })
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<TaskResponse> markTaskAsCompleted(@PathVariable String taskId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        User currentUser = (User) authentication.getPrincipal();

        return new ResponseEntity<>(taskService.markTaskAsCompleted(currentUser.getId(), taskId), HttpStatus.OK);
    }

    @Operation(summary = "Delete a task form the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", 
        content = {
            @Content(
                    mediaType = "application/json"
                )
            }),
            @ApiResponse(responseCode = "404", description = "Resource not found", 
            content = {
                @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "405", description = "Method not allowed/Invalid parameters", 
            content = {
                @Content(mediaType = "application/json")
            })
        })
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable String taskId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        User currentUser = (User) authentication.getPrincipal();

        taskService.deleteTask(currentUser.getId(), taskId);

        return ResponseEntity.ok(Map.of("message", "Se borro la tarea correctamente"));
    }
}
