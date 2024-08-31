package codesquad.bows.global.security.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("인증 실패: {}", exception.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String errorMessage;

        if (exception instanceof BadCredentialsException){
            errorMessage = "아이디 혹은 비밀번호가 틀렸습니다.";
        } else {
            errorMessage = "인증에 실패했습니다.";
        }



        // 예외 메시지를 JSON 형식으로 반환
        response.getWriter().write("{\"error\": \"Authentication failed\", \"message\": \"" + errorMessage + "\"}");
    }
}
