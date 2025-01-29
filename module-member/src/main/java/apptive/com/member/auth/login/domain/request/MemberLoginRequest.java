package apptive.com.member.auth.login.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberLoginRequest {

    @NotBlank(message = "로그인 아이디(이메일)을 확인해주세요.")
    @Pattern(regexp = "[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "이메일 형식을 확인해주세요.")
    private String loginId;

    @NotBlank(message = "로그인 비밀번호를 확인해주세요.")
    @Pattern(regexp = "[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]{1,30}$", message = "비밀번호는 1자에서 30자까지만 가능합니다.")
    private String loginPwd;
}
