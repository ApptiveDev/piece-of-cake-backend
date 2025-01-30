package apptive.com.store.store.model.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreRegistrationRequest {

    @NotBlank(message = "가게명을 확인해주세요.")
    @Size(min = 1, max = 15, message = "가게명은 최소 1자에서 최대 15자까지 가능합니다.")
    private String storeName; // 가게명
    @NotBlank(message = "주소를 확인해주세요.")
    @Size(max = 50, message = "주소 최대 길이는 50자 입니다.")
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
    private String etcStoreInfo; // 기타 가게 정보
    private String snsLink; // SNS 링크

    private String businessRegistrationNumber; // 사업자등록번호
    private String accountHolder; // 예금주
    private String bankName; // 은행명
    private String accountNumber; // 계좌번호

    public void initialize() {
        if (storeName == null) storeName = "";
        if (address == null) address = "";
        if (latitude == 0) latitude = 0.0;
        if (longitude == 0) longitude = 0.0;
        if (contact == null) contact = "";
        if (etcStoreInfo == null) etcStoreInfo = "";
        if (snsLink == null) snsLink = "";
    }
}