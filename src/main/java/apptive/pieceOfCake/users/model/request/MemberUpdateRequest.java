package apptive.pieceOfCake.users.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberUpdateRequest {

    @NotNull
    String email;
    @NotNull
    String loginPwd;

    @NotNull
    String name;
    @NotNull
    String phoneNum;
    @NotNull
    String address;
    @NotNull
    double latitude;
    @NotNull
    double longitude;
    @NotNull
    boolean agreementOfMarketing;
}
