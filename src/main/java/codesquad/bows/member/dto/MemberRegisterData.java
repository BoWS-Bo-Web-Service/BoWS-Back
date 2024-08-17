package codesquad.bows.member.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberRegisterData (

    @NotNull
    @Size(min = 3, max = 20)
    String userId,

    @NotNull
    @Size(min = 5, max = 20)
    String password,

    @NotNull
    String invitationCode,

    @NotNull
    @Size(min = 1, max = 10)
    @Pattern(regexp = "^[a-zA-Z가-힣]+$", message = "이름은 한글 또는 영문만 가능합니다.")
    String name
    ) {
}
