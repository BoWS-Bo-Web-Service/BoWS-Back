package codesquad.bows.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "project") // todo: 테이블 이름
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "PROJECT_NAME", nullable = false)
    private String projectName;

    @Column(name = "DOMAIN", nullable = false)
    private String domain;

    @Column(name = "BACKEND_IMAGE_NAME", nullable = false)
    private String backendImageName;

    @Column(name = "FRONTEND_IMAGE_NAME", nullable = false)
    private String frontendImageName;

    @Column(name = "DB_PASSWORD", nullable = false)
    private String dbPassword;

    @Column(name = "DB_ENDPOINT", nullable = false)
    private String dbEndpoint;

    @Column(name = "DB_USER_NAME", nullable = false)
    private String dbUserName;

    @Column(name = "DB_USER_PASSWORD", nullable = false)
    private String dbUserPassword;
}
