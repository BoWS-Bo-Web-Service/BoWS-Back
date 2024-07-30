package codesquad.bows.service;

import codesquad.bows.dto.ServiceMetadata;
import codesquad.bows.exception.KubeException;
import io.kubernetes.client.extended.kubectl.Kubectl;
import io.kubernetes.client.extended.kubectl.exception.KubectlException;
import io.kubernetes.client.openapi.models.V1Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class KubeExecutor {

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
