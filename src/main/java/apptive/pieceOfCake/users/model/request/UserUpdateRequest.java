package apptive.pieceOfCake.users.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserUpdateRequest {

    String loginId;
    String name;
    String phoneNum;
}
