package com.example.Enterprise.Resource.Suite.ERS.WorkflowLoader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowData {
    private WorkflowContainer workflow;

    public WorkflowContainer getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkflowContainer workflow) {
        this.workflow = workflow;
    }
}
