package apptive.pieceOfCake.auth.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {

    private String loginId;
    private String loginPwd;
}
