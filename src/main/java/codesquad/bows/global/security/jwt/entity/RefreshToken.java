package codesquad.bows.global.security.jwt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "REFRESH_TOKEN")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @NotNull
    @Column(name = "USER_ID")
    private String userId;

    @NotNull
    @Column(name = "EXPIRATION_TIME")
    private LocalDateTime expirationTime;
}
