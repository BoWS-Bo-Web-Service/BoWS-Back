package codesquad.bows.project.dto;

import io.kubernetes.client.openapi.models.V1ContainerState;
import io.kubernetes.client.openapi.models.V1ContainerStatus;

public record ServiceState(
        String imageName,
        String state,
        String reason,
        String message
) {
    public static ServiceState from(V1ContainerStatus status) {
        String state = getState(status.getState());
        String reason = getReason(status.getState());
        String message = getMessage(status.getState());
        return new ServiceState(status.getImage(), state, reason, message);
    }

    private static String getState(V1ContainerState state) {
        if (state.getRunning() != null) return "Running";
        if (state.getWaiting() != null) return "Waiting";
        if (state.getTerminated() != null) return "Terminated";
        return "Unknown";
    }

    private static String getReason(V1ContainerState state) {
        if (state.getWaiting() != null) return state.getWaiting().getReason();
        if (state.getTerminated() != null) return state.getTerminated().getReason();
        return "";
    }

    private static String getMessage(V1ContainerState state) {
        if (state.getWaiting() != null) return state.getWaiting().getMessage();
        if (state.getTerminated() != null) return state.getTerminated().getMessage();
        return "";
    }
}
