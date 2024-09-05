package codesquad.bows.global.security.filter;

import codesquad.bows.global.security.jwt.exception.InvalidTokenSignatureException;
import codesquad.bows.member.exception.BadCredentialException;
import codesquad.bows.member.exception.UnhandledAuthenticationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final HandlerExceptionResolver resolver;

    public CustomAuthenticationFailureHandler(HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("인증 실패: {}", exception.getMessage());
        if (exception instanceof BadCredentialsException){
            resolver.resolveException(request, response, null, new BadCredentialException());
        } else {
            log.error("관리되지 않는 인증 예외가 발생했습니다. : {}", exception.getMessage());
            resolver.resolveException(request, response, null, new UnhandledAuthenticationException());
        }
    }
}
