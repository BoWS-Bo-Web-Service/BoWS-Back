package codesquad.bows.service;


import codesquad.bows.dto.MemberRegisterData;
import codesquad.bows.entity.Member;
import codesquad.bows.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public void register(MemberRegisterData data) {
        if (memberRepository.findByUsername(data.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        Member member = Member.builder()
                .username(data.getUsername())
                .password(passwordEncoder.encode(data.getPassword()))
                .role("Role_USER")
                .build();
        memberRepository.save(member);
    }
}
