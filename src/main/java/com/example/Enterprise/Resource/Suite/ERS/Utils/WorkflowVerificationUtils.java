package com.example.Enterprise.Resource.Suite.ERS.Utils;

import com.example.Enterprise.Resource.Suite.ERS.WorkflowLoader.WorkFlowActions;
import com.example.Enterprise.Resource.Suite.ERS.WorkflowLoader.WorkflowLoader;
import com.example.Enterprise.Resource.Suite.ERS.WorkflowLoader.WorkflowTransition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Component
public class WorkflowVerificationUtils {

    @Autowired
    private WorkflowLoader workflowLoader;

    /**
     * Validates if the action is allowed based on the current status and action.
     *
     * @param transitions   The list of workflow transitions.
     * @param currentState The current status of the task.
     * @param action        The action that is being applied.
     * @return The updated status if the action is valid; null if not.
     */
    public static String getNextState(List<WorkflowTransition> transitions, String currentState, String action) {
        return transitions.stream()
                .filter(t ->
                        (currentState == null && t.getState() == null) ||
                                (t.getState() != null && t.getState().equals(currentState)) ||
                                ("IN_WORKFLOW".equals(t.getTaskStatus()))
                )
                .flatMap(t -> t.getActions() != null ? t.getActions().stream() : Stream.empty())
                .filter(a -> a.getAction().equals(action))
                .map(WorkFlowActions::getNextState)
                .findFirst()
                .orElse(null);
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
        return getNextState(transitions, currentStatus, action) != null;
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
            return getNextState(transitions, status, action);
        } else {
            throw new IllegalArgumentException("Invalid action for the current status.");
        }
    }
}
