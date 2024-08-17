package codesquad.bows.member.controller;

import codesquad.bows.member.dto.MemberRegisterData;
import codesquad.bows.member.dto.UserIdAvailableResponse;
import codesquad.bows.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid MemberRegisterData data) {
        memberService.register(data);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-userId")
    public ResponseEntity<UserIdAvailableResponse> checkUserIdAvailable(@RequestParam String userId){
        return ResponseEntity.ok(memberService.isUserIdExists(userId));
    }
}
