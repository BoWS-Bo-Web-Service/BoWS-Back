package codesquad.bows.project.service;

import codesquad.bows.project.dto.ProjectDetailResponse;
import codesquad.bows.project.dto.ProjectMetadata;
import codesquad.bows.project.dto.ServiceMetadata;
import codesquad.bows.project.exception.DuplicatedDomainException;
import codesquad.bows.project.exception.ProjectNotExistsException;
import codesquad.bows.project.repository.ProjectRepository;

import codesquad.bows.project.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final KubeExecutor kubeExecutor;

    @PostAuthorize("""
        returnObject.createdBy() == principal.username 
        and (hasRole(T(codesquad.bows.member.entity.RoleName).ADMIN.name()) 
        or hasRole(T(codesquad.bows.member.entity.RoleName).READ_ONLY.name()))
        """)
    @Transactional(readOnly = true)
    public ProjectDetailResponse getProjectDetail(Long projectId) {
        if (!projectRepository.existsById(projectId)){
            throw new ProjectNotExistsException();
        }
        ProjectMetadata projectMetadata = projectRepository.getMetadataById(projectId);
        List<ServiceMetadata> serviceMetadataList = kubeExecutor.getServiceMetadataOf(projectMetadata.projectName());
        return ProjectDetailResponse.of(projectMetadata, serviceMetadataList);
    }

    @PreAuthorize("hasAuthority(T(codesquad.bows.member.entity.AuthorityName).PROJECT_EDIT.name())")
    @Transactional
    public void deleteProject(Long projectId, String userId) {
        if (!projectRepository.existsByIdAndCreatedBy(projectId, userId)){
            throw new ProjectNotExistsException();
        }
        projectRepository.deleteById(projectId);
        kubeExecutor.deleteProjectInCluster(projectId);
    }

    @PreAuthorize("hasAuthority(T(codesquad.bows.member.entity.AuthorityName).PROJECT_EDIT.name())")
    @Transactional
    public Long addProject(Project project) {
        if (projectRepository.existsByDomain(project.getDomain())) {
            throw new DuplicatedDomainException();
        }
        Project savedProject = projectRepository.save(project);
        kubeExecutor.createProjectInCluster(savedProject);
        return savedProject.getId();
    }

    @PreAuthorize("hasAuthority(T(codesquad.bows.member.entity.AuthorityName).PROJECT_READ.name())")
    public List<ProjectMetadata> findAllMetaDataOfUser(String userId) {
        return projectRepository.findAllProjectMetadataOfUser(userId);
    }
}
