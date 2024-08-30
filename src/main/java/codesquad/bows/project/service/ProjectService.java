package codesquad.bows.project.service;

import codesquad.bows.project.dto.ProjectDetailResponse;
import codesquad.bows.project.dto.ProjectMetadata;
import codesquad.bows.project.dto.ServiceMetadata;
import codesquad.bows.project.exception.DuplicatedDomainException;
import codesquad.bows.project.exception.DuplicatedProjectNameException;
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
        and hasAuthority(T(codesquad.bows.member.entity.AuthorityName).PROJECT_READ.name())
        """)
    @Transactional(readOnly = true)
    public ProjectDetailResponse getProjectDetail(Long projectId) {
        if (!projectRepository.existsByIdAndIsDeletedIsFalse(projectId)){
            throw new ProjectNotExistsException();
        }
        ProjectMetadata projectMetadata = projectRepository.getMetadataById(projectId);
        List<ServiceMetadata> serviceMetadataList = kubeExecutor.getServiceMetadataOf(projectId);
        return ProjectDetailResponse.of(projectMetadata, serviceMetadataList);
    }

    @PreAuthorize("hasAuthority(T(codesquad.bows.member.entity.AuthorityName).PROJECT_EDIT.name())")
    @Transactional
    public void deleteProject(Long projectId, String userId) {
        if (!projectRepository.existsByIdAndCreatedByAndIsDeletedIsFalse(projectId, userId)){
            throw new ProjectNotExistsException();
        }
        projectRepository.softDeleteById(projectId);
        kubeExecutor.deleteProjectInCluster(projectId);
    }

    @PreAuthorize("hasAuthority(T(codesquad.bows.member.entity.AuthorityName).PROJECT_EDIT.name())")
    @Transactional
    public Long addProject(Project project) {
        verifyProjectInput(project);
        Project savedProject = projectRepository.save(project);
        kubeExecutor.createProjectInCluster(savedProject);
        return savedProject.getId();
    }

    private void verifyProjectInput(Project project){
        if (projectRepository.existsByDomainAndIsDeletedIsFalse(project.getDomain())) {
            throw new DuplicatedDomainException();
        }
        if (projectRepository.existsByProjectNameAndCreatedByAndIsDeletedIsFalse(
                project.getProjectName(), project.getCreatedBy())
        ){
            throw new DuplicatedProjectNameException();
        }
    }

    @PreAuthorize("hasAuthority(T(codesquad.bows.member.entity.AuthorityName).PROJECT_READ.name())")
    public List<ProjectMetadata> findAllMetaDataOfUser(String userId) {
        return projectRepository.findAllProjectMetadataOfUser(userId);
    }
}
