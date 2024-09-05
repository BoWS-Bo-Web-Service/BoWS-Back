package codesquad.bows.global.security.filter;

import codesquad.bows.global.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final String LOGIN_URL = "/api/members/login";

    private final AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager
            , AuthenticationSuccessHandler authSuccessHandler, AuthenticationFailureHandler authFailureHandler) {
        this.authenticationManager = authenticationManager;
        setAuthenticationSuccessHandler(authSuccessHandler);
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
}
