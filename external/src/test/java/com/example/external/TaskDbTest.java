package com.example.external;

import com.example.core.dto.TaskDTO;
import com.example.core.entity.Task;
import com.example.core.model.TaskStatus;
import com.example.core.repos.TaskRepository;
import com.example.core.service.TaskService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class TaskDbTest {
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testFindAllTasks() {
        // Given
        Task task1 = new Task();
        task1.setName("Test Task 1");
        Task task2 = new Task();
        task2.setName("Test Task 2");

        taskRepository.save(task1);
        taskRepository.save(task2);

        // When
        List<TaskDTO> tasks = taskService.findAll();

        // Then
        assertEquals(2, tasks.size());
    }

    @Test
    void testSaveTask() {
        // Given
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setName("New Task");

        // When
        TaskDTO savedTask = taskService.save(taskDTO);

        // Then
        assertNotNull(savedTask);
        assertEquals("New Task", savedTask.getName());
    }

    @Test
    void testFindById_ShouldReturnTask() {
        // Given
        Task task = new Task();
        task.setName("Sample Task");
        task = taskRepository.save(task);

        // When
        Task result = taskService.findById(task.getId());

        // Then
        assertNotNull(result);
        assertEquals(task.getId(), result.getId());
        assertEquals("Sample Task", result.getName());
    }

    @Test
    void testDeleteById_ShouldRemoveTask() {
        // Given
        Task task = new Task();
        task.setName("Task to be deleted");
        task = taskRepository.save(task);

        // When
        taskService.deleteById(task.getId());

        // Then
        Optional<Task> deletedTask = taskRepository.findById(task.getId());
        assertTrue(deletedTask.isEmpty());
    }

    @Test
    void testUpdate_ShouldModifyTask() {
        // Given
        Task task = new Task();
        task.setName("Original Task");
        task = taskRepository.save(task);

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setName("Updated Task");

        // When
        TaskDTO updatedTask = taskService.update(taskDTO);

        // Then
        assertNotNull(updatedTask);
        assertEquals("Updated Task", updatedTask.getName());

        Task foundTask = taskRepository.findById(task.getId()).orElse(null);
        assertNotNull(foundTask);
        assertEquals("Updated Task", foundTask.getName());
    }

    @Test
    void testUpdateStatus_ShouldModifyTaskStatus() {
        // Given
        Task task = new Task();
        task.setName("Task to change status");
        task.setStatus(TaskStatus.NEW);
        task = taskRepository.save(task);

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setStatus(TaskStatus.COMPLETED);

        // When
        TaskDTO updatedTask = taskService.updateStatus(taskDTO);

        // Then
        assertNotNull(updatedTask);
        assertEquals("COMPLETED", updatedTask.getStatus().name());

        Task foundTask = taskRepository.findById(task.getId()).orElse(null);
        assertNotNull(foundTask);
        assertEquals("COMPLETED", foundTask.getStatus().name());
    }

    @Test
    void testSave_ShouldPersistNewTask() {
        // Given
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setName("New Task for Save");

        // When
        TaskDTO savedTask = taskService.save(taskDTO);

        // Then
        assertNotNull(savedTask);
        assertEquals("New Task for Save", savedTask.getName());

        // Ensure task was saved in the repository
        Task foundTask = ((List<Task>)taskRepository
                .findAll())
                .stream()
                .filter(task -> "New Task for Save".equals(task.getName()))
                .findFirst()
                .orElse(null);

        assertNotNull(foundTask);
        assertEquals("New Task for Save", foundTask.getName());
    }
}
