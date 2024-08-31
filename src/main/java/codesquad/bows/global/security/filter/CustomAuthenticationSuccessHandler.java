package codesquad.bows.global.security.filter;

import codesquad.bows.global.security.jwt.JwtTokenProvider;
import codesquad.bows.global.security.jwt.TokenService;
import codesquad.bows.global.security.user.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;

    public CustomAuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider, TokenService tokenService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenService = tokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        List<String> authorities = userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .toList();
        String accessToken = jwtTokenProvider.createAccessToken(userDetails.getUsername(), authorities);
        String refreshToken = jwtTokenProvider.createRefreshToken(userDetails.getUsername());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, String> tokenResponse = new HashMap<>();
        tokenResponse.put("accessToken", accessToken);
        tokenResponse.put("refreshToken", refreshToken);
        new ObjectMapper().writeValue(response.getWriter(), tokenResponse);
    }
}
