package codesquad.bows.project.service;

import codesquad.bows.project.dto.ProjectDetailResponse;
import codesquad.bows.project.dto.ProjectMetadata;
import codesquad.bows.project.dto.ServiceMetadata;
import codesquad.bows.project.entity.Project;
import codesquad.bows.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final KubeExecutor kubeExecutor;

    /*
    TODO : 해당 프로젝트의 생성자와 READ_ONLY 들이 확인할 수 있어야 한다.
        그러러면 프로젝트를 생성할 때 사용자의 ID가 PROJECT에 포함되어야 한다.
     */
    @PreAuthorize("hasAuthority(T(codesquad.bows.member.entity.AuthorityName).PROJECT_READ.name())")
    @Transactional(readOnly = true)
    public ProjectDetailResponse getProjectDetail(Long projectId) {
        ProjectMetadata projectMetadata = projectRepository.getMetadataById(projectId);
        List<ServiceMetadata> serviceMetadataList = kubeExecutor.getServiceMetadataOf(projectMetadata.projectName());
        return ProjectDetailResponse.of(projectMetadata, serviceMetadataList);
    }

    @PreAuthorize("hasAuthority(T(codesquad.bows.member.entity.AuthorityName).PROJECT_EDIT.name())")
    @Transactional
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
        //kubeExecutor.deleteProjectInCluster(projectId);
    }

    @PreAuthorize("hasAuthority(T(codesquad.bows.member.entity.AuthorityName).PROJECT_EDIT.name())")
    @Transactional
    public void addProject(Project project) {
        Project savedProject = projectRepository.save(project);
        //kubeExecutor.createProjectInCluster(savedProject);
    }

    @PreAuthorize("hasAuthority(T(codesquad.bows.member.entity.AuthorityName).PROJECT_READ.name())")
    public List<ProjectMetadata> findAllMetaData() {
        return projectRepository.findAllProjectMetadata();
    }
}
