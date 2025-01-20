package apptive.pieceOfCake.auth.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthLoginRequest {

    String email; // 로그인 아이디 (이메일)
    String loginPwd; // 로그인 비밀번호
    String role;
}
