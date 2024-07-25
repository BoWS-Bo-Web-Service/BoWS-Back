package codesquad.bows.controller;

import codesquad.bows.dto.ApplicationCreateRequest;
import codesquad.bows.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/create")
    public ResponseEntity<String> createApplication(@RequestBody ApplicationCreateRequest request) throws IOException, InterruptedException {
        return ResponseEntity.ok(applicationService.create(request));
    }

    @GetMapping("/destroy")
    public ResponseEntity<String> destroyApplication() throws IOException, InterruptedException {
        return ResponseEntity.ok(applicationService.destroy());
    }

    @GetMapping("/healthcheck")
    @ResponseBody
    public String healthCheck(){
        return "healthy";
    }
}