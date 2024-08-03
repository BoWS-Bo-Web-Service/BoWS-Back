package codesquad.bows.project.controller;

import codesquad.bows.common.SecurityUtils;
import codesquad.bows.project.dto.ProjectCreateRequest;
import codesquad.bows.project.dto.ProjectMetadata;
import codesquad.bows.project.dto.ProjectDetailResponse;
import codesquad.bows.project.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<Long> createProject(@Valid @RequestBody ProjectCreateRequest request) {
        return ResponseEntity.ok(projectService.addProject(request.toEntity(SecurityUtils.getLoginUserId())));
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
