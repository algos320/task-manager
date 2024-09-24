package com.example.core.service;

import com.example.core.mapper.TaskMapper;
import com.example.core.dto.TaskDTO;
import com.example.core.entity.Task;
import com.example.core.exception.ErrorCodeException;
import com.example.core.repos.TaskRepository;
import com.example.core.utils.NullAwareBeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<TaskDTO> findAll() {
        log.debug("Find all tasks");
        return ((List<Task>) taskRepository.findAll())
                .stream()
                .map(taskMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public Task findById(Long id) {
        log.debug("Find task by id: {}", id);
        return taskRepository.findById(id).orElseThrow(() -> new ErrorCodeException("Task not found", 404));
    }

    public void deleteById(Long id) {
        log.debug("Delete task by id: {}", id);
        taskRepository.deleteById(id);
    }

    public TaskDTO update(TaskDTO taskDTO) {
        log.debug("Update task: {}", taskDTO);
        Task task = findById(taskDTO.getId());
        NullAwareBeanUtils.copyNonNullProperties(taskDTO, task);
        return taskMapper.mapToDTO(taskRepository.save(task));
    }

    public TaskDTO updateStatus(TaskDTO taskDTO) {
        log.debug("Update status task: {}", taskDTO);
        if (Objects.isNull(taskDTO.getStatus())) {
            throw new ErrorCodeException("Invalid status", 400);
        }
        Task task = findById(taskDTO.getId());
        task.setStatus(taskDTO.getStatus());
        return taskMapper.mapToDTO(taskRepository.save(task));
    }

    public TaskDTO save(TaskDTO taskDTO) {
        log.debug("Save task: {}", taskDTO);
        Task task = taskMapper.mapToEntity(taskDTO);
        return taskMapper.mapToDTO(taskRepository.save(task));
    }
}
