package codesquad.bows.common;

import codesquad.bows.global.security.user.CustomUserDetails;
import codesquad.bows.member.exception.UnauthenticatedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class SecurityUtils {
    public static String getLoginUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        throw new UnauthenticatedException();
    }

    public static boolean hasAuthority(String targetAuthority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(targetAuthority));
    }
}
