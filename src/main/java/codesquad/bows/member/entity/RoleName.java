package codesquad.bows.member.entity;

import lombok.Getter;

import java.util.Set;

@Getter
public enum RoleName {
    ADMIN(Set.of(AuthorityName.ADMIN, AuthorityName.PROJECT_EDIT, AuthorityName.PROJECT_READ)),
    USER(Set.of(AuthorityName.PROJECT_EDIT, AuthorityName.PROJECT_READ)),
    READ_ONLY(Set.of(AuthorityName.PROJECT_READ));

    private final Set<AuthorityName> authorities;

    RoleName(Set<AuthorityName> authorityNames) {
        this.authorities = authorityNames;
    }
}
