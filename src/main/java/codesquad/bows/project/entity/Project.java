package codesquad.bows.project.entity;

import codesquad.bows.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROJECT")
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "PROJECT_NAME")
    private String projectName;

    @NotNull
    @Column(name = "DOMAIN")
    private String domain;

    @NotNull
    @Column(name = "BACKEND_IMAGE_NAME")
    private String backendImageName;

    @NotNull
    @Column(name = "FRONTEND_IMAGE_NAME")
    private String frontendImageName;

    @NotNull
    @Column(name = "DB_STORAGE_SIZE")
    private Integer dbStorageSize;

    @Lob
    @Column(name = "DB_SCHEMA")
    private String dbSchema;

    @NotNull
    @Column(name = "DB_PASSWORD")
    private String dbPassword;

    @NotNull
    @Column(name = "DB_ENDPOINT")
    private String dbEndpoint;

    @NotNull
    @Column(name = "DB_USER_NAME")
    private String dbUserName;

    @NotNull
    @Column(name = "DB_USER_PASSWORD")
    private String dbUserPassword;

    public Project(String projectName, String domain, String backendImageName, String frontendImageName,
                   Integer dbStorageSize, String dbSchema,
                   String dbPassword, String dbEndpoint, String dbUserName, String dbUserPassword) {
        this.projectName = projectName;
        this.domain = domain;
        this.backendImageName = backendImageName;
        this.frontendImageName = frontendImageName;
        this.dbStorageSize = dbStorageSize;
        this.dbSchema = dbSchema;
        this.dbPassword = dbPassword;
        this.dbEndpoint = dbEndpoint;
        this.dbUserName = dbUserName;
        this.dbUserPassword = dbUserPassword;
    }

    public Map<String, String> getProjectOptions() {
        Map<String, String> helmCLIArguments = new HashMap<>();
        helmCLIArguments.put("projectName", this.projectName);
        helmCLIArguments.put("projectId", String.valueOf(this.id));
        helmCLIArguments.put("domain", this.domain);
        helmCLIArguments.put("app.backend.image.name", this.backendImageName);
        helmCLIArguments.put("app.frontend.image.name", this.frontendImageName);
        helmCLIArguments.put("app.db.storageSize", this.dbStorageSize + "Gi");
        helmCLIArguments.put("app.db.schema", this.dbSchema);
        helmCLIArguments.put("app.db.env.MYSQL_ROOT_PASSWORD", this.dbPassword);
        helmCLIArguments.put("app.db.env.MYSQL_DATABASE", this.dbEndpoint);
        helmCLIArguments.put("app.db.env.MYSQL_USER", this.dbUserName);
        helmCLIArguments.put("app.db.env.MYSQL_PASSWORD", this.dbUserPassword);

        return helmCLIArguments;
    }
}
