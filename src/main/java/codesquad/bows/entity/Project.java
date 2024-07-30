package codesquad.bows.entity;

import codesquad.bows.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
}
