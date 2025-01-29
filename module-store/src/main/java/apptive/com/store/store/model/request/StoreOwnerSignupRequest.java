package apptive.com.store.store.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreOwnerSignupRequest {

    @NotBlank(message = "로그인 아이디를 확인해주세요.")
    @Pattern(regexp = "[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]{1,15}$", message = "아이디는 최대 15자까지만 가능합니다.")
    String loginId;

    @NotBlank(message = "로그인 비밀번호를 확인해주세요.")
    @Pattern(regexp = "[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]{1,30}$", message = "비밀번호는 최대 30자까지만 가능합니다.")
    String loginPwd;
}
