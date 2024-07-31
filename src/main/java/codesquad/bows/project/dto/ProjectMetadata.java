package codesquad.bows.project.dto;

import java.time.LocalDateTime;

public record ProjectMetadata(
        String projectName,
        LocalDateTime projectCreatedTime,
        String domain
) {
}