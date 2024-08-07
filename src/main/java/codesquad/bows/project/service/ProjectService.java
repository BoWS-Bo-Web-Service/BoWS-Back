package codesquad.bows.project.service;

import codesquad.bows.project.dto.ProjectDetailResponse;
import codesquad.bows.project.dto.ProjectMetadata;
import codesquad.bows.project.dto.ServiceMetadata;
import codesquad.bows.project.exception.DuplicatedDomainException;
import codesquad.bows.project.exception.ProjectNotExistsException;
import codesquad.bows.project.repository.ProjectRepository;

import codesquad.bows.project.entity.Project;
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
        if (!projectRepository.existsById(projectId)){
            throw new ProjectNotExistsException();
        }
        ProjectMetadata projectMetadata = projectRepository.getMetadataById(projectId);
        List<ServiceMetadata> serviceMetadataList = kubeExecutor.getServiceMetadataOf(projectMetadata.projectName());
        return ProjectDetailResponse.of(projectMetadata, serviceMetadataList);
    }

    @Transactional
    public void deleteProject(Long projectId) {
        if (!projectRepository.existsById(projectId)){
            throw new ProjectNotExistsException();
        }
        projectRepository.deleteById(projectId);
        kubeExecutor.deleteProjectInCluster(projectId);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Transactional
    public Long addProject(Project project) {
        if (projectRepository.existsByDomain(project.getDomain())) {
            throw new DuplicatedDomainException();
        }
        Project savedProject = projectRepository.save(project);
        kubeExecutor.createProjectInCluster(savedProject);
        return savedProject.getId();
    }

    public List<ProjectMetadata> findAllMetaData() {
        return projectRepository.findAllProjectMetadata();
    }
}
