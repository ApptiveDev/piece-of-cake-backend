package apptive.pieceOfCake.users.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberCheckSameEmail {

    @NotBlank(message = "로그인 아이디를 입력해주세요.")
    String loginId;
}
