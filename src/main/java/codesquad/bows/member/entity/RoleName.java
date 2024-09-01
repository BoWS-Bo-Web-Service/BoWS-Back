package codesquad.bows.member.entity;

import lombok.Getter;

import java.util.Set;

@Getter
public enum RoleName {
    ADMIN(Set.of(AuthorityName.PROJECT_CREATE, AuthorityName.PROJECT_EDIT_ALL, AuthorityName.PROJECT_READ_ALL)),
    USER(Set.of(AuthorityName.PROJECT_CREATE, AuthorityName.PROJECT_EDIT_OWN, AuthorityName.PROJECT_READ_OWN)),
    READ_ONLY(Set.of(AuthorityName.PROJECT_READ_ALL));

    private final Set<AuthorityName> authorities;

    RoleName(Set<AuthorityName> authorityNames) {
        this.authorities = authorityNames;
    }
}
