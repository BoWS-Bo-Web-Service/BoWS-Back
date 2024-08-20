package codesquad.bows.member.service;


import codesquad.bows.member.dto.MemberRegisterData;
import codesquad.bows.member.dto.UserIdAvailableResponse;
import codesquad.bows.member.entity.Member;
import codesquad.bows.member.entity.Role;
import codesquad.bows.member.entity.RoleName;
import codesquad.bows.member.exception.InvitationCodeMismatchException;
import codesquad.bows.member.exception.RoleNotExistsException;
import codesquad.bows.member.exception.UsernameAlreadyExistsException;
import codesquad.bows.member.repository.MemberRepository;
import codesquad.bows.member.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${security.invitationCode}")
    private String INVITATION_CODE;

    public void register(MemberRegisterData data) {
        validRegisterData(data);

        Optional<Role> userRoleOptional = roleRepository.findByName(RoleName.USER.name());
        if(userRoleOptional.isEmpty()){
            log.error("해당하는 Role이 존재하지 않습니다");
            throw new RoleNotExistsException();
        }

        Member member = Member.builder()
                .userId(data.userId())
                .password(passwordEncoder.encode(data.password()))
                .name(data.name())
                .roles(Set.of(userRoleOptional.get()))
                .build();
        memberRepository.save(member);
    }

    private void validRegisterData(MemberRegisterData data) {
        if (!data.invitationCode().equals(INVITATION_CODE)) {
            throw new InvitationCodeMismatchException();
        }

        if (memberRepository.existsByUserId(data.userId())) {
            throw new UsernameAlreadyExistsException();
        }
    }

    public UserIdAvailableResponse isUserIdExists(String userId) {
        boolean userIdExists = memberRepository.existsByUserId(userId);
        return new UserIdAvailableResponse(userId, !userIdExists);
    }
}
