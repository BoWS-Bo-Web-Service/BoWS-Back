package codesquad.bows.global.security.jwt.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest (
        @NotBlank
        String refreshToken
){
}
