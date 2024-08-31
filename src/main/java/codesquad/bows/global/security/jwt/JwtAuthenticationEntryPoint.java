package codesquad.bows.global.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver resolver;

    // HandlerExceptionResolver의 빈이 두 종류가 있기 때문에 @Qualifier로 handlerExceptionResolver를 주입받을 것이라고 명시해줘야 한다.
    public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        // JwtAuthenticationFilter에서 request에 담아서 보내준 예외를 처리
        resolver.resolveException(request, response, null, (Exception) request.getAttribute("exception"));
    }
}
