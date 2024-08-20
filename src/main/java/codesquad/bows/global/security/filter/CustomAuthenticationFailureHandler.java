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
    // 인증 시도가 실패하는 경우에 이 메소드가 실행된다.
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("인증 실패: {}", exception.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String errorMessage;

        if (exception instanceof UsernameNotFoundException) {
            errorMessage = "존재하지 않는 사용자입니다.";
        } else if (exception instanceof BadCredentialsException){
            errorMessage = "비밀번호가 틀렸습니다.";
        } else {
            errorMessage = "인증에 실패했습니다.";
        }



        // 예외 메시지를 JSON 형식으로 반환
        response.getWriter().write("{\"error\": \"Authentication failed\", \"message\": \"" + errorMessage + "\"}");
    }
}
