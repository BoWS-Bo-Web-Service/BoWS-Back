package codesquad.bows.service;


import codesquad.bows.dto.ProjectMetaData;
import codesquad.bows.entity.Project;
import codesquad.bows.entity.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public void addProject(Project project) {
        projectRepository.save(project);
    }

    public List<ProjectMetaData> findAllMetaData() {
        return projectRepository.findAllBy();
    }
}
