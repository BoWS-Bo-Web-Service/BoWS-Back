package codesquad.bows.member.config;

import codesquad.bows.member.entity.Authority;
import codesquad.bows.member.entity.Role;
import codesquad.bows.member.entity.AuthorityName;
import codesquad.bows.member.entity.RoleName;
import codesquad.bows.member.repository.RoleRepository;
import codesquad.bows.member.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    @PostConstruct
    public void initData() {
        // Authority 초기화
        for (AuthorityName authorityName : AuthorityName.values()) {
            authorityRepository.save(Authority.builder()
                    .name(authorityName.name())
                    .build());
        }

        // Role 초기화 (Authority를 포함하기 때문에 Authority 초기화 이후에 선언)
        for (RoleName roleName : RoleName.values()) {
            Set<Authority> authorities = new HashSet<>();
            for (AuthorityName authorityName : roleName.getAuthorities()) {
                Authority authority = authorityRepository.findByName(authorityName.name())
                        .orElseThrow(() -> new IllegalArgumentException("해당하는 권한을 찾을 수 없습니다."));
                authorities.add(authority);
            }
            roleRepository.save(Role.builder()
                    .name(roleName.name())
                    .authorities(authorities)
                    .build());
        }
    }
}
