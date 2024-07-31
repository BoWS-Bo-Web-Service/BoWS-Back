package codesquad.bows.service;

import codesquad.bows.dto.ServiceMetadata;
import codesquad.bows.entity.Project;
import codesquad.bows.exception.KubeException;
import codesquad.bows.exception.DeletionFailedException;
import codesquad.bows.exception.CreationFailedException;
import io.kubernetes.client.extended.kubectl.Kubectl;
import io.kubernetes.client.extended.kubectl.exception.KubectlException;
import io.kubernetes.client.openapi.models.V1Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KubeExecutor {

    @Value("${kube.helm.repoName}")
    private String HELM_REPO_NAME;

    @Value("${kube.helm.chartName}")
    private String CHART_NAME;


    public void createProjectInCluster(Project project){
        Map<String, String> projectOptions = project.getProjectOptions();
        String arguments = projectOptions.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(",", "--set ", ""));

        String command = "helm install " + project.getId() + " " + HELM_REPO_NAME + "/" + CHART_NAME + " " + arguments;
        BashExecutor.executeCommand(command, CreationFailedException::new);
    }

    public void deleteProjectInCluster(Long projectId){
        String command = "helm uninstall " + projectId;
        BashExecutor.executeCommand(command, DeletionFailedException::new);
    }

    public List<ServiceMetadata> getServiceMetadataOf(String projectName) {
        List<V1Service> services = new ArrayList<>();
        try {
            services = Kubectl.get(V1Service.class).namespace("my-app").execute();
        } catch (KubectlException e) {
            log.error(e.getMessage());
            throw new KubeException();
        }
        return services.stream()
                .filter(service -> service.getMetadata().getLabels().containsValue(projectName))
                .map(ServiceMetadata::of)
                .toList();
    }
}
