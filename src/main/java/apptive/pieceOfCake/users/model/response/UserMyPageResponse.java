package apptive.pieceOfCake.users.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserMyPageResponse {

    Long userId;
    String name;
    String phoneNum;
    String email;
    String address;
    String loginPwd;
}
