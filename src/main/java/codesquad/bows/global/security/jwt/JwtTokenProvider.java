package codesquad.bows.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey; //JWT 토큰 객체 키를 저장할 시크릿 키
    private final Long accessTokenExpiredMs = 30 * 60 * 1000L;
    private final Long refreshTokenExpiredMs = 7 * 24 * 60 * 60 * 1000L;

    public JwtTokenProvider(@Value("${security.jwtSecretKey}") String secret) {
        this.secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8)
                , Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    public String getUserId(String token) {

        return parseClaims(token)
                .get("userId",String.class);
    }

    public String getRole(String token) {

        return parseClaims(token)
                .get("role", String.class);
    }

    public boolean isExpired(String token) {

        return parseClaims(token)
                .getExpiration()
                .before(new Date());
    }

    public String createAccessToken(String userId) {

        return Jwts.builder()
                .claim("userId", userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiredMs))
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String userId) {

        return Jwts.builder()
                .claim("userId", userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiredMs))
                .signWith(secretKey)
                .compact();
    }

    public String getJwtFromRequestHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public LocalDateTime getExpirationDateFromToken(String token) {
        Claims claims = parseClaims(token);
        Date expiration = claims.getExpiration();
        return expiration.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
