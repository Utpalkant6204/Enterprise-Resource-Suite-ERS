package com.example.Enterprise.Resource.Suite.ERS.Mapper;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.TaskDTO;
import com.example.Enterprise.Resource.Suite.ERS.Entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends GenericMapper<Task, TaskDTO> {
}
