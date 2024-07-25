package codesquad.bows.service;

import codesquad.bows.dto.ApplicationCreateRequest;
import codesquad.bows.service.helm.HelmCommandExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final String RELEASE_NAME = "example";
    private final String HELM_REPO_NAME = "sampleapp";
    private final String CHART_NAME = "sampleapp";

    private final HelmCommandExecutor helmCommandExecutor;

    public String create(ApplicationCreateRequest request) throws IOException, InterruptedException {
       int exitCode = helmCommandExecutor.executeInstall(RELEASE_NAME, HELM_REPO_NAME, CHART_NAME, request.toHelmCLIArguments());
       if (exitCode == 1) {
                return "App Creation Failed";
       }
       return "App Creation Succeed";
    }

    public String destroy() throws IOException, InterruptedException {
        int exitCode = helmCommandExecutor.executeUninstall(RELEASE_NAME);
        if (exitCode == 1) {
            return "App Destroy Failed";
        }
        return "App Destroy Succeed";
    }
}
