package codesquad.bows.global.security.jwt;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest (
        @NotBlank
        String refreshToken
){
}