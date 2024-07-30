package codesquad.bows.dto;

import java.time.LocalDateTime;

public record ProjectMetadata(
        String projectName,
        LocalDateTime projectCreatedTime,
        String domain
) {
}
