package codesquad.bows.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectDetailResponse(
        String projectName,
        LocalDateTime projectCreatedTime,
        String domain,
        List<ServiceMetadata> serviceMetadata
) {

    public static ProjectDetailResponse of(ProjectMetadata projectMetadata, List<ServiceMetadata> services){
        return new ProjectDetailResponse(
                projectMetadata.projectName(),
                projectMetadata.projectCreatedTime(),
                projectMetadata.domain(),
                services
        );
    }
}
