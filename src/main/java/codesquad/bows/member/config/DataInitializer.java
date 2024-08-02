package codesquad.bows.member.config;

import codesquad.bows.member.entity.*;
import codesquad.bows.member.repository.AuthorityRepository;
import codesquad.bows.member.repository.MemberRepository;
import codesquad.bows.member.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initData() {
        // Authority 초기화
        initAuthorities();

        // Role 초기화 (Authority를 포함하기 때문에 Authority 초기화 이후에 선언)
        initRoles();

        // Admin 계정 등록
        createMember("admin", "admin", RoleName.ADMIN);

        // Readonly 계정 등록
        createMember("guest", "guest", RoleName.READ_ONLY);
    }

    private void initRoles() {
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

    private void initAuthorities() {
        for (AuthorityName authorityName : AuthorityName.values()) {
            authorityRepository.save(Authority.builder()
                    .name(authorityName.name())
                    .build());
        }
    }

    private void createMember(String username, String password, RoleName roleName) {
        if (memberRepository.findByUsername(username).isPresent())
            return;

        Role adminRole = roleRepository.findByName(roleName.name())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 Role이 존재하지 않습니다."));

        Member adminMember = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(Set.of(adminRole))
                .build();
        memberRepository.save(adminMember);
    }
}
