package com.example.Enterprise.Resource.Suite.ERS.WorkflowLoader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowContainer {
    private List<WorkflowTransition> transitions;

    public List<WorkflowTransition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<WorkflowTransition> transitions) {
        this.transitions = transitions;
    }
}
