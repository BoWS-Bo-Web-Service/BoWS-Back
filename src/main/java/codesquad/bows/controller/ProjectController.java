package codesquad.bows.controller;

import codesquad.bows.entity.Project;
import codesquad.bows.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<Project> getProjects() {
        return projectService.findAll();
    }

    @PostMapping
    public void createProject(@RequestBody Project project) {
        projectService.addProject(project);
    }
}
