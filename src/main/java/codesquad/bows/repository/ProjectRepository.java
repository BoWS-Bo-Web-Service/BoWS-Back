package codesquad.bows.repository;

import codesquad.bows.dto.ProjectMetadata;
import codesquad.bows.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(""" 
           SELECT new codesquad.bows.dto.ProjectMetadata(p.projectName, p.createdAt, p.domain)
           FROM Project p
           WHERE p.id = :id
           """)
    ProjectMetadata getMetadataById(@Param("id") Long id);
}
