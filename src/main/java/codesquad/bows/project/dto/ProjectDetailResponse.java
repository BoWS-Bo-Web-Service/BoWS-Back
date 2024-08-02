package codesquad.bows.project.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectDetailResponse(
        String projectName,
        LocalDateTime projectCreatedTime,
        String domain,
        Long createdBy,
        List<ServiceMetadata> serviceMetadata
) {

    public static ProjectDetailResponse of(ProjectMetadata projectMetadata, List<ServiceMetadata> services){
        return new ProjectDetailResponse(
                projectMetadata.projectName(),
                projectMetadata.projectCreatedTime(),
                projectMetadata.domain(),
                projectMetadata.createdBy(),
                services
        );
    }
}
