package codesquad.bows.project.service;

import codesquad.bows.common.SecurityUtils;
import codesquad.bows.member.entity.AuthorityName;
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

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAuthority;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final KubeExecutor kubeExecutor;

    @PostAuthorize("""
        (returnObject.createdBy() == principal.username 
            and hasAuthority(T(codesquad.bows.member.entity.AuthorityName).PROJECT_READ_OWN.name()))
        or hasAuthority(T(codesquad.bows.member.entity.AuthorityName).PROJECT_READ_ALL.name())
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

    @PreAuthorize("hasAuthority(T(codesquad.bows.member.entity.AuthorityName).PROJECT_EDIT_OWN.name())")
    @Transactional
    public void deleteProject(Long projectId, String userId) {
        if (!projectRepository.existsByIdAndCreatedByAndIsDeletedIsFalse(projectId, userId)){
            throw new ProjectNotExistsException();
        }
        projectRepository.softDeleteById(projectId);
        kubeExecutor.deleteProjectInCluster(projectId);
    }

    @PreAuthorize("hasAuthority(T(codesquad.bows.member.entity.AuthorityName).PROJECT_CREATE.name())")
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

    @PreAuthorize("""
            hasAnyAuthority(T(codesquad.bows.member.entity.AuthorityName).PROJECT_READ_ALL.name()
                ,T(codesquad.bows.member.entity.AuthorityName).PROJECT_READ_OWN.name())
            """)
    public List<ProjectMetadata> getProjectList() {
        if (SecurityUtils.hasAuthority(AuthorityName.PROJECT_READ_ALL.name())) {
            return projectRepository.findAllProjectMetadata();
        }
        else {
            return projectRepository.findAllProjectMetadataOfUser(SecurityUtils.getLoginUserId());
        }

    }
}
