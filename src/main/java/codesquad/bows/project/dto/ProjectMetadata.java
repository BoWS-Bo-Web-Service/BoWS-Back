package codesquad.bows.project.dto;

import java.time.LocalDateTime;

public record ProjectMetadata(
        Long projectId,
        String projectName,
        LocalDateTime projectCreatedTime,
        String domain,
        String createdBy
) {
}