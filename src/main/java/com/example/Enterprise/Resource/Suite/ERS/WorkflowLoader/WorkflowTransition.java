package com.example.Enterprise.Resource.Suite.ERS.WorkflowLoader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowTransition {
    private String state;
    private List<WorkFlowActions> actions;

    private String status;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<WorkFlowActions> getActions() {
        return actions;
    }

    public void setActions(List<WorkFlowActions> actions) {
        this.actions = actions;
    }

    public String getTaskStatus() {
        return status;
    }

    public void setTaskStatus(String status) {
        this.status = status;
    }
}
