package codesquad.bows.service.helm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

@Slf4j
@Service
public class HelmCommandExecutor {

    private final String FLAG = "--set ";

    public int executeInstall(String releaseName, String helmRepoName, String chartName, Map<String, String> arguments) throws IOException, InterruptedException {
        StringBuilder argumentBuilder = new StringBuilder();
        argumentBuilder.append(FLAG);
        for(Map.Entry<String, String> entry : arguments.entrySet()){
            argumentBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
        }
        argumentBuilder.deleteCharAt(argumentBuilder.length()-1);

        String[] commands = {"/bin/sh", "-c", "helm install " + releaseName + " " + helmRepoName + "/" + chartName + " " + argumentBuilder.toString().trim()};

        return execute(commands);
    }

    public int executeUninstall(String releaseName) throws IOException, InterruptedException {
        String[] commands = {"/bin/sh", "-c", "helm uninstall " + releaseName};

        return execute(commands);
    }

    private int execute(String[] commands) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        // 출력 스트림 읽기
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            log.info(line);
        }

        // 프로세스 종료 코드 확인
        int exitCode = process.waitFor();
        log.info("\nExited with error code : " + exitCode);

        return exitCode;
    }
}