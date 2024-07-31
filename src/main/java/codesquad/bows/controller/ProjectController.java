package codesquad.bows.controller;

import codesquad.bows.dto.ProjectCreateRequest;
import codesquad.bows.dto.ProjectMetadata;
import codesquad.bows.service.ProjectService;
import jakarta.validation.Valid;
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

    @PostMapping
    public ResponseEntity<Void> createProject(@Valid @RequestBody ProjectCreateRequest request) {
        projectService.addProject(request.toEntity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDetailResponse> getProjectDetail(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.getProjectDetail(projectId));
    }

    @GetMapping
    public ResponseEntity<List<ProjectMetadata>> getProjectList() {
        return ResponseEntity.ok(projectService.findAllMetaData());
    }
}
