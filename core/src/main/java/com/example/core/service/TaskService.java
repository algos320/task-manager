package com.example.core.service;

import com.example.core.TaskMapper;
import com.example.core.dto.TaskDTO;
import com.example.core.entity.Task;
import com.example.core.repos.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<TaskDTO> findAll() {
        return taskMapper.mapToDTO((List<Task>) taskRepository.findAll());
    }
}
