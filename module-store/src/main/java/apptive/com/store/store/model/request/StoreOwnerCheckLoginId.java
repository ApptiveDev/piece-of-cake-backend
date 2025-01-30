package apptive.com.store.store.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreOwnerCheckLoginId {

    @NotBlank(message = "로그인 아이디(이메일)을 확인해주세요.")
    @Pattern(regexp = "[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]{1,30}$", message = "이메일 형식을 확인해주세요. (1~30자)")
    String loginId;
}
