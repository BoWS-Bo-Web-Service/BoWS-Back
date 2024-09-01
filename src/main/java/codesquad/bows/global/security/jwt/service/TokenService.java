package codesquad.bows.global.security.jwt.service;

import codesquad.bows.global.security.jwt.JwtTokenProvider;
import codesquad.bows.global.security.jwt.entity.RefreshToken;
import codesquad.bows.global.security.jwt.exception.InvalidTokenException;
import codesquad.bows.global.security.jwt.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    public String refreshAccessToken(HttpServletRequest request) {
        String refreshToken = jwtTokenProvider.getJwtFromRequestHeader(request);
        String username = jwtTokenProvider.getUserId(refreshToken);
        RefreshToken savedToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(InvalidTokenException::new);

        if (!savedToken.getUserId().equals(username)) {
            throw new InvalidTokenException();
        }

        return jwtTokenProvider.createAccessToken(username);
    }

    public void saveRefreshToken(String refreshToken, String userId) {
        refreshTokenRepository.findByUserId(userId)
                .ifPresent(token -> refreshTokenRepository.deleteById(token.getId()));

        LocalDateTime expiration = jwtTokenProvider.getExpirationDateFromToken(refreshToken);

        RefreshToken refreshTokenData = RefreshToken.builder()
                .refreshToken(refreshToken)
                .userId(userId)
                .expirationTime(expiration)
                .build();

        refreshTokenRepository.save(refreshTokenData);
    }
}
