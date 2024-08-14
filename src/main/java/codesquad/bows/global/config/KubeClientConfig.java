package codesquad.bows.global.config;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.IOException;

@Configuration
public class KubeClientConfig {

    @Value("${kube.configPath}")
    private String configPath;

    @Bean
    public ApiClient apiClient() throws IOException {
        String kubeConfigPath = "C:\\Users\\shaki" + configPath;
        ApiClient client = ClientBuilder
                .kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath)))
                .build();
        io.kubernetes.client.openapi.Configuration.setDefaultApiClient(client);
        return client;
    }
}
