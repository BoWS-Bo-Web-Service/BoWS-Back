package codesquad.bows.member.service;


import codesquad.bows.member.dto.MemberRegisterData;
import codesquad.bows.member.entity.Member;
import codesquad.bows.member.entity.Role;
import codesquad.bows.member.entity.RoleName;
import codesquad.bows.member.repository.MemberRepository;
import codesquad.bows.member.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public void register(MemberRegisterData data) {
        if (memberRepository.findByUsername(data.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        Role userRole = roleRepository.findByName(RoleName.USER.name())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 Role이 존재하지 않습니다."));

        Member member = Member.builder()
                .username(data.getUsername())
                .password(passwordEncoder.encode(data.getPassword()))
                .roles(Set.of(userRole))
                .build();
        memberRepository.save(member);
    }
}
