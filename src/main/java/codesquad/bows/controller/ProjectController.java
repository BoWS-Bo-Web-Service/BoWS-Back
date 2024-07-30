package codesquad.bows.controller;

import codesquad.bows.dto.ProjectMetadata;
import codesquad.bows.entity.Project;
import codesquad.bows.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import codesquad.bows.dto.ProjectDetailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDetailResponse> getProjectDetail(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.getProjectDetail(projectId));
    }

    @GetMapping
    public ResponseEntity<List<ProjectMetadata>> getProjectList() {
        return ResponseEntity.ok(projectService.findAllMetaData());
    }

    @PostMapping
    public void createProject(@RequestBody Project project) {
        projectService.addProject(project);
    }
}
