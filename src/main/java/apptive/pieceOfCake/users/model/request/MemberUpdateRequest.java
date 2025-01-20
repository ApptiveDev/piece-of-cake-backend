package apptive.pieceOfCake.users.model.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberUpdateRequest {

    @NotBlank(message = "로그인 아이디(이메일)을 확인해주세요.")
    @Pattern(regexp = "[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "이메일 형식을 확인해주세요.")
    String email;
    @NotBlank(message = "로그인 비밀번호를 확인해주세요.")
    @Pattern(regexp = "[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]{1,30}$", message = "비밀번호는 최대 30자까지만 가능합니다.")
    String loginPwd;

    @NotBlank(message = "이름을 확인해주세요.")
    @Size(min = 1, max = 10, message = "이름은 최소 1 자에서 최대 10 자까지 가능합니다.")
    String name;
    @NotBlank(message = "전화번호를 확인해주세요.")
    @Pattern(regexp = "^(\\d{3})-(\\d{4})-(\\d{4})$",
            message = "전화번호는 000-0000-0000 형식이어야 합니다.")
    String phoneNum;
    @NotBlank(message = "주소를 확인해주세요.")
    @Size(max = 40, message = "주소 최대 길이는 40자 입니다.")
    String address;
    @NotBlank(message = "위도를 확인해주세요.")
    @Min(value = -90, message = "위도의 범위는 -90~+90 입니다.")
    @Max(value = 90, message = "위도의 범위는 -90~+90 입니다.")
    double latitude;
    @NotBlank(message = "경도를 확인해주세요.")
    @Min(value = -180, message = "경도의 범위는 -180~+180 입니다.")
    @Max(value = 180, message = "경도의 범위는 -180~+180 입니다.")
    double longitude;

    @NotBlank(message = "마케팅 수신 여부를 확인해주세요.")
    boolean agreementOfMarketing;
}
