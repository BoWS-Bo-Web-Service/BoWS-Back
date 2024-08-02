package codesquad.bows.project.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectDetailResponse(
        Long projectId,
        String projectName,
        LocalDateTime projectCreatedTime,
        String domain,
        Long createdBy,
        List<ServiceMetadata> serviceMetadata
) {

    public static ProjectDetailResponse of(ProjectMetadata projectMetadata, List<ServiceMetadata> services){
        return new ProjectDetailResponse(
                projectMetadata.projectId(),
                projectMetadata.projectName(),
                projectMetadata.projectCreatedTime(),
                projectMetadata.domain(),
                projectMetadata.createdBy(),
                services
        );
    }
}
