package codesquad.bows.project.dto;

import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.openapi.models.V1ServicePort;

import java.util.List;

public record ServiceMetadata(
        String serviceName,
        List<String> externalIps,
        List<Integer> ports,
        String age,
        String imageName,
        String state,
        String stateReason,
        String stateMessage
) {

    public static ServiceMetadata of(V1Service service, ServiceState serviceState) {

        String name = service.getMetadata().getName();
        List<String> externalIPs = service.getSpec().getExternalIPs();
        List<V1ServicePort> v1ServicePorts = service.getSpec().getPorts();
        List<Integer> ports = v1ServicePorts.stream()
                .map(V1ServicePort::getPort)
                .toList();
        String creationTimestamp = service.getMetadata().getCreationTimestamp().toString();

        return new ServiceMetadata(name, externalIPs, ports, creationTimestamp,
                serviceState.imageName(), serviceState.state(), serviceState.reason(), serviceState.message());
    }
}
