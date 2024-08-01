package codesquad.bows.common;

import codesquad.bows.member.entity.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Long getLoginUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getId();
        }
        // TODO : 로그인 하지 않은 사용자를 위한 익셉션이 필요
        throw new IllegalArgumentException("로그인 하지 않은 상태입니다.");
    }
}
