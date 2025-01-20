package apptive.pieceOfCake.users.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberMyPageResponse {

    private Long userId;
    private String name;
    private String phoneNum;
    private String email;
    private String address;
    private String loginPwd;
    private boolean agreementOfMarketing;
}
