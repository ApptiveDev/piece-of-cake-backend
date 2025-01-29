package apptive.com.store.store.model.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreRegistrationRequest {

    @NotBlank(message = "이름을 확인해주세요.")
    @Size(min = 1, max = 15, message = "가게명은 최소 1자에서 최대 15자까지 가능합니다.")
    private String name; // 가게명
    @NotBlank(message = "주소를 확인해주세요.")
    @Size(max = 40, message = "주소 최대 길이는 40자 입니다.")
    private String address; // 주소
    @NotBlank(message = "위도를 확인해주세요.")
    @Min(value = -90, message = "위도의 범위는 -90~+90 입니다.")
    @Max(value = 90, message = "위도의 범위는 -90~+90 입니다.")
    private double latitude; // 위도
    @NotBlank(message = "경도를 확인해주세요.")
    @Min(value = -180, message = "경도의 범위는 -180~+180 입니다.")
    @Max(value = 180, message = "경도의 범위는 -180~+180 입니다.")
    private double longitude; // 경도
    @NotBlank(message = "가게 연락처를 확인해주세요.")
    @Pattern(regexp = "^(\\d{3})-(\\d{3,4})-(\\d{4})$",
            message = "가게 연락처는 000-000(0)-0000 형식이어야 합니다.")
    private String contact; // 가게 연락처
    @NotBlank(message = "전화번호를 확인해주세요.")
    @Pattern(regexp = "^(\\d{3})-(\\d{4})-(\\d{4})$",
            message = "전화번호는 000-0000-0000 형식이어야 합니다.")
    private String phoneNum; // 대표 연락
    private String businessHours; // 영업시간
    private String closedDays; // 휴무일
    private String sLink; // SNS 링크
    private String profileIntroduction; // 프로필 설명

    public void initialize() {
        if (name == null) name = "";
        if (address == null) address = "";
        if (latitude == 0) latitude = 0.0;
        if (longitude == 0) longitude = 0.0;
        if (contact == null) contact = "";
        if (phoneNum == null) phoneNum = "";
        if (businessHours == null) businessHours = "";
        if (closedDays == null) closedDays = "";
        if (sLink == null) sLink = "";
        if (profileIntroduction == null) profileIntroduction = "";
    }
}