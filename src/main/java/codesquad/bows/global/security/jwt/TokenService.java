package codesquad.bows.global.security.jwt;

import codesquad.bows.global.security.jwt.exception.InvalidRefreshTokenException;
import codesquad.bows.global.security.jwt.exception.RefreshTokenExpiredException;
import codesquad.bows.global.security.user.CustomUserDetails;
import codesquad.bows.global.security.user.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    private final RefreshTokenRepository refreshTokenRepository;

    public String refreshAccessToken(HttpServletRequest request) {
        String refreshToken = jwtTokenProvider.getJwtFromRequestHeader(request);

        // 토큰이 없거나, 유효하지 않거나, 만료된 경우
        if (refreshToken == null || jwtTokenProvider.isExpired(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }

        String username = jwtTokenProvider.getUsername(refreshToken);
        RefreshToken savedToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(InvalidRefreshTokenException::new);

        if (!savedToken.getUsername().equals(username)) {
            throw new InvalidRefreshTokenException();
        }

        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .toList();

        System.out.println("\n*******************\n\nauthorities = " + authorities);

        // 새로운 액세스 토큰 생성
        return jwtTokenProvider.createAccessToken(username, authorities);
    }
}
