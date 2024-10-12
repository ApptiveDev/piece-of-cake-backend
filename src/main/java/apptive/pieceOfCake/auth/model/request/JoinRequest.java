package apptive.pieceOfCake.auth.model.request;

import apptive.pieceOfCake.member.model.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JoinRequest {

    private String loginId; // 로그인 아이디
    private String loginPwd; // 로그인 비밀번호
    private String phoneNum; // 전화번호
    private String email; // 이메일
    private String name; // 이름
}
