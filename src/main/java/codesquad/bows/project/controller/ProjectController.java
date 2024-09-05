package codesquad.bows.project.controller;

import codesquad.bows.common.SecurityUtils;
import codesquad.bows.project.dto.ProjectCreateRequest;
import codesquad.bows.project.dto.ProjectMetadata;
import codesquad.bows.project.dto.ProjectDetailResponse;
import codesquad.bows.project.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createProject(
            @Valid @ModelAttribute ProjectCreateRequest request,
            @RequestPart(value = "dbSchemaFile") MultipartFile dbSchemaFile
    ) {
        return ResponseEntity.ok(
                projectService.addProject(
                        request.toEntity(dbSchemaFile, SecurityUtils.getLoginUserId())
                )
        );
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId, SecurityUtils.getLoginUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDetailResponse> getProjectDetail(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.getProjectDetail(projectId));
    }

    @GetMapping
    public ResponseEntity<List<ProjectMetadata>> getProjectList() {
        return ResponseEntity.ok(projectService.getProjectList(SecurityUtils.getLoginUserId()));
    }
}
