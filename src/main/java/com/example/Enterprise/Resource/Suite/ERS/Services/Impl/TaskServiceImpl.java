package com.example.Enterprise.Resource.Suite.ERS.Services.Impl;

import com.example.Enterprise.Resource.Suite.ERS.DTOS.TaskDTO;
import com.example.Enterprise.Resource.Suite.ERS.Repositories.TaskRepository;
import com.example.Enterprise.Resource.Suite.ERS.Services.Interface.TaskService;
import com.example.Enterprise.Resource.Suite.ERS.Utils.WorkflowVerificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    private WorkflowVerificationUtils workflowVerificationUtils;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, WorkflowVerificationUtils workflowVerificationUtils) {
        this.taskRepository = taskRepository;
        this.workflowVerificationUtils = workflowVerificationUtils;
    }

    @Override
    public String createTask(TaskDTO taskDTO) {

        try {
            return workflowVerificationUtils.verifyWorkFlowActions(taskDTO.getAction(), taskDTO.getStatus(), "TaskWorkflow.json");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
