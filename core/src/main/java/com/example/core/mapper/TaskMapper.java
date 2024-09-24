package com.example.core.mapper;

import com.example.core.dto.TaskDTO;
import com.example.core.entity.Task;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDTO mapToDTO(Task task);
    Task mapToEntity(TaskDTO taskDTO);
}
