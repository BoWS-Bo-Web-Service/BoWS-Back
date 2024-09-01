package codesquad.bows.global.security.jwt.service;

import codesquad.bows.global.security.jwt.JwtTokenProvider;
import codesquad.bows.global.security.jwt.entity.RefreshToken;
import codesquad.bows.global.security.jwt.exception.InvalidTokenException;
import codesquad.bows.global.security.jwt.repository.RefreshTokenRepository;
import codesquad.bows.global.security.user.CustomUserDetails;
import codesquad.bows.global.security.user.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    private final RefreshTokenRepository refreshTokenRepository;

    public String refreshAccessToken(HttpServletRequest request) {
        String refreshToken = jwtTokenProvider.getJwtFromRequestHeader(request);
        String username = jwtTokenProvider.getUsername(refreshToken);
        RefreshToken savedToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(InvalidTokenException::new);

        if (!savedToken.getUsername().equals(username)) {
            throw new InvalidTokenException();
        }

        return jwtTokenProvider.createAccessToken(username);
    }

    public void saveRefreshToken(String refreshToken, String username) {
        LocalDateTime expiration= jwtTokenProvider.getExpirationDateFromToken(refreshToken);

        RefreshToken refreshTokenData = RefreshToken.builder()
                .refreshToken(refreshToken)
                .username(username)
                .expirationTime(expiration)
                .build();

        refreshTokenRepository.save(refreshTokenData);
    }
}
