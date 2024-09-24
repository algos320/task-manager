package com.example.external;

import com.example.core.dto.TaskDTO;
import com.example.core.entity.Task;
import com.example.core.exception.ErrorCodeException;
import com.example.core.mapper.TaskMapper;
import com.example.core.repos.TaskRepository;
import com.example.core.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    void findAll_ShouldReturnListOfTaskDTOs() {
        // Given
        List<Task> tasks = List.of(new Task(), new Task());
        List<TaskDTO> taskDTOs = List.of(new TaskDTO(), new TaskDTO());

        when(taskRepository.findAll()).thenReturn(tasks);
        when(taskMapper.mapToDTO(any(Task.class))).thenReturn(new TaskDTO());

        // When
        List<TaskDTO> result = taskService.findAll();

        // Then
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
        verify(taskMapper, times(2)).mapToDTO(any(Task.class));
    }

    @Test
    void findById_ShouldReturnTask_WhenTaskExists() {
        // Given
        Long taskId = 1L;
        Task task = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // When
        Task result = taskService.findById(taskId);

        // Then
        assertNotNull(result);
        assertEquals(task, result);
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void findById_ShouldThrowErrorCodeException_WhenTaskNotFound() {
        // Given
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ErrorCodeException.class, () -> taskService.findById(taskId));
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void deleteById_ShouldCallRepositoryDelete() {
        // Given
        Long taskId = 1L;

        // When
        taskService.deleteById(taskId);

        // Then
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    void update_ShouldUpdateTask() {
        // Given
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        Task task = new Task();

        when(taskRepository.findById(taskDTO.getId())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.mapToDTO(any(Task.class))).thenReturn(taskDTO);

        // When
        TaskDTO updatedTask = taskService.update(taskDTO);

        // Then
        assertNotNull(updatedTask);
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(taskMapper, times(1)).mapToDTO(any(Task.class));
    }
}
