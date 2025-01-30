package apptive.com.store.store.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreOwnerSignupRequest {

    @NotBlank(message = "로그인 아이디(이메일)을 확인해주세요.")
    @Pattern(regexp = "[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]{1,30}$", message = "이메일 형식을 확인해주세요. (1~30자)")
    String loginId;

    @NotBlank(message = "로그인 비밀번호를 확인해주세요.")
    @Pattern(regexp = "[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]{1,30}$", message = "비밀번호는 최소 1자에서 최대 30자까지만 가능합니다.")
    String loginPwd;

    @NotBlank(message = "이름을 확인해주세요.")
    @Size(min = 1, max = 10, message = "이름은 최소 1자에서 최대 10 자까지 가능합니다.")
    String ownerName;

    @NotBlank(message = "전화번호를 확인해주세요.")
    @Pattern(regexp = "^(\\d{3})-(\\d{4})-(\\d{4})$",
            message = "전화번호는 000-0000-0000 형식이어야 합니다.")
    String phoneNum;
}
