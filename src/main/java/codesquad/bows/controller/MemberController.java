package codesquad.bows.controller;

import codesquad.bows.dto.MemberRegisterData;
import codesquad.bows.entity.Member;
import codesquad.bows.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public void register(@RequestBody MemberRegisterData data) {
        memberService.register(data);
    }

}
