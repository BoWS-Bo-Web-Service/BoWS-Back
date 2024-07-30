package codesquad.bows.controller;

import codesquad.bows.dto.ProjectDetailResponse;
import codesquad.bows.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDetailResponse> getProjectDetail(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.getProjectDetail(projectId));
    }
}
