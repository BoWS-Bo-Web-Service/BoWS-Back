package codesquad.bows.project.service;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Supplier;

@Slf4j
public class BashExecutor {

    public static void executeCommand(String command, Supplier<RuntimeException> exceptionSupplier) {
        String[] commands = {"/bin/sh", "-c", command};
        try {
            if (execute(commands) == 1) {
                throw exceptionSupplier.get();
            }
        } catch (IOException | InterruptedException e) {
            log.error("Error occurred while executing command: {}", command, e);
            throw exceptionSupplier.get();
        }
    }

    private static int execute(String[] commands) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        Process process = processBuilder.start();

        try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String line;
            while ((line = errorReader.readLine()) != null) {
                log.error(line);
            }
        }

        return process.waitFor();
    }
}
