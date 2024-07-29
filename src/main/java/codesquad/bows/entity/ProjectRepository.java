package codesquad.bows.entity;

import codesquad.bows.dto.ProjectMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<ProjectMetaData> findAllBy();
}
