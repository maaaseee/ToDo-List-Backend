package com.aieta.springboot.todo_app.infrastructure.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.aieta.springboot.todo_app.application.dto.task.CreateTaskRequest;
import com.aieta.springboot.todo_app.application.dto.task.TaskResponse;
import com.aieta.springboot.todo_app.application.dto.task.UpdateTaskRequest;
import com.aieta.springboot.todo_app.application.service.TaskService;

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

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

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
    public ResponseEntity<List<TaskResponse>> getAllTasks(
        @Parameter(name = "userId", description = "ID from the User", required = true)
        @RequestParam String userId
        ) {
        return ResponseEntity.ok(taskService.getAllTasks(userId));
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
        @Parameter(name = "userId", description = "ID from the User", required = true)
        @RequestParam String userId, 
        
        @Parameter(name = "taskId", description = "ID from the Task", required = true)
        @PathVariable String taskId
        ) {
        return ResponseEntity.ok(taskService.getTaskById(userId, taskId));
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
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(
        @Parameter(name = "userId", description = "ID from the User", required = true)
        @RequestParam String userId, 

        @Parameter(name = "completed", description = "State of the task, completed or not", required = true)
        @RequestParam(defaultValue = "false") boolean completed) {
        return ResponseEntity.ok(taskService.getTasksByStatus(userId, completed));
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
        return new ResponseEntity<>(taskService.createTask(task), HttpStatus.CREATED);
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
        @RequestBody @Valid UpdateTaskRequest updateTask, 
        @RequestParam String userId) {
             
        return new ResponseEntity<>(taskService.updateTask(taskId, updateTask, userId), HttpStatus.OK);
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
    public ResponseEntity<TaskResponse> markTaskAsCompleted(@RequestParam String userId, @PathVariable String taskId) {
        return new ResponseEntity<>(taskService.markTaskAsCompleted(userId, taskId), HttpStatus.OK);
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
    public ResponseEntity<Void> deleteTask(@RequestParam String userId, @PathVariable String taskId) {
        taskService.deleteTask(userId, taskId);
        return ResponseEntity.noContent().build();
    }
}
