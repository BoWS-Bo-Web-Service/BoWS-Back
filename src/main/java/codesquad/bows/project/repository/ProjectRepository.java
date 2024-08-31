package codesquad.bows.project.repository;

import codesquad.bows.project.dto.ProjectMetadata;
import codesquad.bows.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(""" 
           SELECT new codesquad.bows.project.dto.ProjectMetadata(p.id, p.projectName, p.createdAt, p.domain, p.createdBy)
           FROM Project p
           WHERE p.id = :id AND p.isDeleted = false
           """)
    ProjectMetadata getMetadataById(@Param("id") Long id);

    @Query(""" 
           SELECT new codesquad.bows.project.dto.ProjectMetadata(p.id, p.projectName, p.createdAt, p.domain, p.createdBy)
           FROM Project p
           WHERE p.createdBy = :userId AND p.isDeleted = false 
           """)
    List<ProjectMetadata> findAllProjectMetadataOfUser(String userId);

    @Modifying
    @Query("UPDATE Project p SET p.isDeleted = true WHERE p.id = :id")
    void softDeleteById(Long id);

    boolean existsByDomainAndIsDeletedIsFalse(String domain);

    boolean existsByIdAndIsDeletedIsFalse(Long id);

    boolean existsByIdAndCreatedByAndIsDeletedIsFalse(Long id, String createdBy);

    boolean existsByProjectNameAndCreatedByAndIsDeletedIsFalse(String projectName, String createdBy);
}
