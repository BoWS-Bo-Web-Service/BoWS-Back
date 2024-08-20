package codesquad.bows.global.security.filter;

import codesquad.bows.common.JwtTokenProvider;
import codesquad.bows.global.security.user.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final String LOGIN_URL = "/api/members/login";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtLoginFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, AuthenticationFailureHandler authFailureHandler) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        setAuthenticationFailureHandler(authFailureHandler);
        setFilterProcessesUrl(LOGIN_URL); // 로그인 요청을 받을 URL
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            // JSON 데이터를 파싱하여 username과 password를 추출
            Map<String, String> jsonBody = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            String username = jsonBody.get("userId");
            String password = jsonBody.get("password");

            // authenticationManager 에 인증을 위한 username, password를 전달하기 위한 객체.
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

            return authenticationManager.authenticate(authRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();

        List<String> authorities = userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .toList();
        String token = jwtTokenProvider.createJwt(userDetails.getUsername(), authorities, 3600000L);

        // Authorization Header에 보내려면 이것.
        // response.addHeader(AUTHORIZATION_HEADER, TOKEN_PREFIX + token);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, String> tokenResponse = new HashMap<>();
        tokenResponse.put("token", token);
        new ObjectMapper().writeValue(response.getWriter(), tokenResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        getFailureHandler().onAuthenticationFailure(request, response, failed);
    }
}
