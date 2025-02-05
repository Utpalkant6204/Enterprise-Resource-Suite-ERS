package com.example.Enterprise.Resource.Suite.ERS.Utils;

import com.example.Enterprise.Resource.Suite.ERS.WorkflowLoader.WorkflowLoader;
import com.example.Enterprise.Resource.Suite.ERS.WorkflowLoader.WorkflowTransition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class WorkflowVerificationUtils {

    @Autowired
    private WorkflowLoader workflowLoader;

    /**
     * Validates if the action is allowed based on the current status and action.
     *
     * @param transitions   The list of workflow transitions.
     * @param currentStatus The current status of the task.
     * @param action        The action that is being applied.
     * @return The updated status if the action is valid; null if not.
     */
    public static String getUpdatedStatus(List<WorkflowTransition> transitions, String currentStatus, String action) {
        Optional<WorkflowTransition> transition = transitions.stream()
                .filter(t -> (t.getCurrentStatus() == null && currentStatus == null) ||
                        (t.getCurrentStatus() != null && t.getCurrentStatus().equals(currentStatus)))
                .filter(t -> t.getAction().equals(action))
                .findFirst();

        return transition.map(WorkflowTransition::getUpdatedStatus).orElse(null);
    }

    /**
     * Verifies if the action is valid for the given task's current status.
     *
     * @param transitions   The list of transitions from the workflow.
     * @param currentStatus The current status of the task.
     * @param action        The action to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidAction(List<WorkflowTransition> transitions, String currentStatus, String action) {
        return getUpdatedStatus(transitions, currentStatus, action) != null;
    }

    /**
     * Validates the action for the task and updates its status if valid.
     *
     * @param action          current action.
     * @param status          current status.
     * @param workFlowFileName workflow fileName.
     * @return The updated task if the action is valid, throws an exception otherwise.
     * @throws IllegalArgumentException if the action is not valid for the current status.
     */
    public String verifyWorkFlowActions(String action, String status, String workFlowFileName) {
        List<WorkflowTransition> transitions = workflowLoader.loadWorkflowFromFile(workFlowFileName);
        // Validate if the action is allowed for the current status
        if (isValidAction(transitions, status, action)) {
            // Get the updated status for the task based on the action
            return getUpdatedStatus(transitions, status, action);
        } else {
            throw new IllegalArgumentException("Invalid action for the current status.");
        }
    }
}
