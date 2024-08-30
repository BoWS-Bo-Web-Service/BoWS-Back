package codesquad.bows.project.service;

import codesquad.bows.project.dto.ServiceMetadata;
import codesquad.bows.project.dto.ServiceState;
import codesquad.bows.project.entity.Project;
import codesquad.bows.project.exception.KubeException;
import codesquad.bows.project.exception.DeletionFailedException;
import codesquad.bows.project.exception.CreationFailedException;
import io.kubernetes.client.extended.kubectl.Kubectl;
import io.kubernetes.client.extended.kubectl.exception.KubectlException;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1Secret;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.proto.V1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    private final CoreV1Api coreV1Api;

    private final String namespace = "my-app";


    // ProjectId를 Release 이름으로 설정하여 Helm 배포 커맨드 생성
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

        String secretName = "certificate-" + projectId;
        try {
            Kubectl.delete(V1Secret.class).namespace(namespace).name(secretName).execute();
        } catch (KubectlException e) {
            log.error(e.getMessage());

            throw new KubeException();
        }
    }

    public List<ServiceMetadata> getServiceMetadataOf(Long projectId) {
        try {
            List<V1Service> services = coreV1Api
                    .listNamespacedService(namespace)
                    .labelSelector("projectId" + "=" + projectId)
                    .execute().getItems();

            return services.stream()
                    .map(service -> ServiceMetadata.of(service, getServiceStateFrom(service)))
                    .toList();

        } catch (ApiException | NullPointerException e) {
            log.error(e.getMessage());

            throw new KubeException();
        }
    }

    // Service 당 Application 실행을 위한 Pod 한개, Pod 1개에 Container 1개로 전제
    public ServiceState getServiceStateFrom(V1Service service) {
        try {
            String labelSelector = getLabelSelector(service);
            V1Pod pod = coreV1Api.listNamespacedPod(namespace)
                    .labelSelector(labelSelector)
                    .execute()
                    .getItems()
                    .get(0);

            return ServiceState.from(pod.getStatus().getContainerStatuses().get(0));

        } catch (ApiException | NullPointerException e) {
            log.error(e.getMessage());

            throw new KubeException();
        }
    }

    private String getLabelSelector(V1Service service) {
        try{
            return service.getSpec().getSelector().entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining(","));
        } catch (NullPointerException e) {
            log.error(e.getMessage());

            throw new KubeException();
        }
    }
}
