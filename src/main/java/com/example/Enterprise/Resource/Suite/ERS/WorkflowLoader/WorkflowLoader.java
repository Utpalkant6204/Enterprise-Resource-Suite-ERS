package com.example.Enterprise.Resource.Suite.ERS.WorkflowLoader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class WorkflowLoader {
    public List<WorkflowTransition> loadWorkflowFromFile(String workflowFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            WorkflowData data = objectMapper.readValue(
                    new ClassPathResource("workflows/" + workflowFile).getFile(),
                    WorkflowData.class
            );
            return data.getWorkflow().getTransitions();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load workflow JSON file", e);
        }
    }
}
