package codesquad.bows.service;

import codesquad.bows.dto.ProjectDetailResponse;
import codesquad.bows.dto.ProjectMetadata;
import codesquad.bows.dto.ServiceMetadata;
import codesquad.bows.repository.ProjectRepository;

import codesquad.bows.entity.Project;
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

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public void addProject(Project project) {
        projectRepository.save(project);
    }

    public List<ProjectMetadata> findAllMetaData() {
        return projectRepository.findAllProjectMetadata();
    }
}
