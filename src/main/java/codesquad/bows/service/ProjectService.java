package codesquad.bows.service;

import codesquad.bows.dto.ProjectDetailResponse;
import codesquad.bows.dto.ProjectMetadata;
import codesquad.bows.dto.ServiceMetadata;
import codesquad.bows.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final KubeExecutor kubeExecutor;

    public ProjectDetailResponse getProjectDetail(Long projectId) {
        ProjectMetadata projectMetadata = projectRepository.getMetadataById(projectId);
        List<ServiceMetadata> serviceMetadataList = kubeExecutor.getServiceMetadataOf(projectMetadata.projectName());
        return ProjectDetailResponse.of(projectMetadata, serviceMetadataList);
    }
}
