package com.example.core;

import com.example.core.dto.TaskDTO;
import com.example.core.entity.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    List<TaskDTO> mapToDTO(List<Task> task);
}
