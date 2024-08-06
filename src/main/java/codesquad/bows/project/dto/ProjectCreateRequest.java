package codesquad.bows.project.dto;

import codesquad.bows.project.entity.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProjectCreateRequest(

        @NotBlank
        @Pattern(regexp = "^(?!\\d+$)([a-z0-9]+([.\\-_][a-z0-9]+)*)$",
                message = "소문자, 숫자와 점(.), 대시(-), 밑줄(_)만 사용할 수 있습니다 (특수문자는 소문자, 숫자 사이 사용)")
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