package codesquad.bows.member.entity;

import java.util.Set;

public enum RoleName {
    ADMIN(Set.of(AuthorityName.ADMIN, AuthorityName.PROJECT_EDIT, AuthorityName.PROJECT_READ)),
    USER(Set.of(AuthorityName.PROJECT_EDIT, AuthorityName.PROJECT_READ)),
    READ_ONLY(Set.of(AuthorityName.PROJECT_READ));

    private final Set<AuthorityName> authorities;

    RoleName(Set<AuthorityName> authorityNames) {
        this.authorities = authorityNames;
    }

    public Set<AuthorityName> getAuthorities() {
        return authorities;
    }
}
