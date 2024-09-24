package com.example.external.controller;

import com.example.core.dto.TaskDTO;
import com.example.core.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;

    @PostMapping()
    @Operation(summary = "Create Task", description = "Creates a new task and returns it.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task created successfully, ID returned",
                    content = @Content(schema = @Schema(type = "integer", format = "int64")))
    })
    public Long createTask(@RequestBody TaskDTO taskDTO) {
        log.info("Create task: {}", taskDTO);
        return taskService
                .save(taskDTO)
                .getId();
    }

    @GetMapping()
    @Operation(summary = "Get All Tasks", description = "Returns a list of all tasks.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of tasks retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskDTO.class,
                            example = "[{\"id\": 1, \"title\": \"Task 1\", \"status\": \"OPEN\"}, {\"id\": 2, \"title\": \"Task 2\", \"status\": \"COMPLETED\"}]"))))
    })
    public List<TaskDTO> getTasks() {
        log.info("Get all task");
        return taskService.findAll();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Task", description = "Deletes a task by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task deleted successfully, ID returned",
                    content = @Content(schema = @Schema(type = "integer", format = "int64")))
    })
    public Long deleteTask(@PathVariable Long id) {
        log.info("Delete task {}", id);
        taskService.deleteById(id);
        return id;
    }

    @PutMapping()
    @Operation(summary = "Update Task Status", description = "Updates the status of a task by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status updated successfully",
                    content = @Content(schema = @Schema(implementation = TaskDTO.class)))
    })
    public TaskDTO updateTaskStatus(@RequestBody TaskDTO taskDTO) {
        log.info("Update task {}", taskDTO);
        return taskService.updateStatus(taskDTO);
    }

    @PatchMapping()
    @Operation(summary = "Patch Task", description = "Partially updates a task by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully",
                    content = @Content(schema = @Schema(implementation = TaskDTO.class)))
    })
    public TaskDTO patchTask(@RequestBody TaskDTO taskDTO) {
        log.info("Patch task {}", taskDTO);
        return taskService.update(taskDTO);
    }

}
