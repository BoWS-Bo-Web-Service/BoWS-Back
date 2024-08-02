package codesquad.bows.project.repository;

import codesquad.bows.project.dto.ProjectMetadata;
import codesquad.bows.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(""" 
           SELECT new codesquad.bows.project.dto.ProjectMetadata(p.projectName, p.createdAt, p.domain, p.createdBy)
           FROM Project p
           WHERE p.id = :id
           """)
    ProjectMetadata getMetadataById(@Param("id") Long id);

    @Query(""" 
           SELECT new codesquad.bows.project.dto.ProjectMetadata(p.projectName, p.createdAt, p.domain, p.createdBy)
           FROM Project p
           """)
    List<ProjectMetadata> findAllProjectMetadata();
}
