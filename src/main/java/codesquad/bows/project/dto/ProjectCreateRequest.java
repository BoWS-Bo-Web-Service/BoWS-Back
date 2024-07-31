package codesquad.bows.project.dto;

import codesquad.bows.project.entity.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProjectCreateRequest(

        @NotBlank
        @Pattern(regexp = "^[a-z0-9]([-a-z0-9]*[a-z0-9])?(\\.[a-z0-9]([-a-z0-9]*[a-z0-9])?)*$",
        message = "영문 소문자와 숫자, 특수문자로만 이루어져야 합니다(특수문자로 시작하거나 끝나선 안 됩니다)")
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

        @NotNull
        @Size(min = 5, max = 30)
        String dbPassword,

        @NotNull
        @Size(min = 5, max = 30)
        String dbEndpoint,

        @NotNull
        @Size(min = 5, max = 30)
        String dbUserName,

        @NotNull
        @Size(min = 5, max = 30)
        String dbUserPassword
) {

    public Project toEntity() {
        return new Project(projectName, domain, backendImageName, frontendImageName,
                dbPassword, dbEndpoint, dbUserName, dbUserPassword);
    }
}