package codesquad.bows.service;

import codesquad.bows.dto.ProjectDetailResponse;
import codesquad.bows.dto.ProjectMetadata;
import codesquad.bows.dto.ServiceMetadata;
import codesquad.bows.repository.ProjectRepository;

import codesquad.bows.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final KubeExecutor kubeExecutor;

    @Transactional(readOnly = true)
    public ProjectDetailResponse getProjectDetail(Long projectId) {
        ProjectMetadata projectMetadata = projectRepository.getMetadataById(projectId);
        List<ServiceMetadata> serviceMetadataList = kubeExecutor.getServiceMetadataOf(projectMetadata.projectName());
        return ProjectDetailResponse.of(projectMetadata, serviceMetadataList);
    }

    @Transactional
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
        kubeExecutor.deleteProjectInCluster(projectId);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Transactional
    public void addProject(Project project) {
        Project savedProject = projectRepository.save(project);
        kubeExecutor.createProjectInCluster(savedProject);
    }

    public List<ProjectMetadata> findAllMetaData() {
        return projectRepository.findAllProjectMetadata();
    }
}
