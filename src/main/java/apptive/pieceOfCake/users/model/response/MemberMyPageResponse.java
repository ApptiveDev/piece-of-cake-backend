package apptive.pieceOfCake.users.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberMyPageResponse {

    Long userId;
    String name;
    String phoneNum;
    String email;
    String address;
    String loginPwd;
}
