package com.example.Enterprise.Resource.Suite.ERS.Services.Interface;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.TaskDTO;
import org.springframework.http.ResponseEntity;

public interface TaskService {
    public String createTask(TaskDTO taskDTO);
}
