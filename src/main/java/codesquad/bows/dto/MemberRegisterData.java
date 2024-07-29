package codesquad.bows.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberRegisterData {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
