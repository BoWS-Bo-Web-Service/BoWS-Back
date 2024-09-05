package codesquad.bows.global.security.filter;

import codesquad.bows.global.security.jwt.JwtTokenProvider;
import codesquad.bows.global.security.jwt.exception.ExpiredTokenException;
import codesquad.bows.global.security.jwt.exception.InvalidTokenSignatureException;
import codesquad.bows.global.security.jwt.exception.MalformedTokenException;
import codesquad.bows.global.security.jwt.exception.UnhandledJwtException;
import codesquad.bows.global.security.user.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {"/api/members/register", "/api/members/check-userId"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.getJwtFromRequestHeader(request);
        try {

            if (token != null && !jwtTokenProvider.isExpired(token)) {
                String username = jwtTokenProvider.getUserId(token);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                SecurityContextHolder.clearContext();
            }
        } catch (JwtException e) {
            if (e instanceof MalformedJwtException) {
                request.setAttribute("exception", new MalformedTokenException());
            }
            else if (e instanceof ExpiredJwtException) {
                request.setAttribute("exception", new ExpiredTokenException());
            }
            else if (e instanceof SignatureException) {
                request.setAttribute("exception", new InvalidTokenSignatureException());
            }
            else {
                log.error("핸들링되지 않는 JwtException 이 발생헀습니다. : " + e.getMessage(), e);
                request.setAttribute("exception", new UnhandledJwtException());
            }
        } catch (Exception e) {
            request.setAttribute("exception", e); // JwtAuthenticationEntryPoint 에서 사용할 속성 추가
        }

        // 사용자 정의 필터를 만들어서 필터체인에 추가했기 때문에, 필터체인을 타고 다음 필터로 요청을 전달하는 메소드.
         filterChain.doFilter(request, response);
    }
}
