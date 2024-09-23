package com.example.external.controller;

import com.example.core.dto.TaskDTO;
import com.example.core.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/")
    public List<TaskDTO> createTask() {

    }

    @GetMapping("/")
    public List<TaskDTO> getTasks() {
        log.info("Get all task");
        return taskService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        log.info();
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable Long id) {}

    @PatchMapping("/{id}")
    public void patchTask(@PathVariable Long id) {}

}
