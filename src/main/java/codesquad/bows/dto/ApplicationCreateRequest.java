package codesquad.bows.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashMap;
import java.util.Map;

public record ApplicationCreateRequest (

        @NotBlank
        @Size(max = 30)
        String projectName,

        @NotBlank
        @Size(max = 50)
        String domain,

        @NotBlank
        @Size(max = 30)
        String backendImageName,

        @NotBlank
        @Size(max = 30)
        String frontendImageName,

        @NotBlank
        @Size(min = 5, max = 30)
        String dbPassword,

        @NotBlank
        @Size(min = 5, max = 30)
        String dbEndpoint,

        @NotBlank
        @Size(min = 5, max = 30)
        String dbUserName,

        @NotBlank
        @Size(min = 5, max = 30)
        String dbUserPassword
) {

    public Map<String, String> toHelmCLIArguments() {
        Map<String, String> helmCLIArguments = new HashMap<>();
        helmCLIArguments.put("projectName", this.projectName());
        helmCLIArguments.put("domain", this.domain());
        helmCLIArguments.put("app.backend.image.name", this.backendImageName());
        helmCLIArguments.put("app.frontend.image.name", this.frontendImageName());
        helmCLIArguments.put("app.db.env.MYSQL_ROOT_PASSWORD", this.dbPassword());
        helmCLIArguments.put("app.db.env.MYSQL_DATABASE", this.dbEndpoint());
        helmCLIArguments.put("app.db.env.MYSQL_USER", this.dbUserName());
        helmCLIArguments.put("app.db.env.MYSQL_PASSWORD", this.dbUserPassword());

        return helmCLIArguments;
    }
}