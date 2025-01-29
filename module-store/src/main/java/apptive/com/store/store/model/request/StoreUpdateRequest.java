package apptive.com.store.store.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreUpdateRequest {

    @NotBlank(message = "이름을 확인해주세요.")
    @Size(min = 1, max = 15, message = "가게명은 최소 1자에서 최대 15자까지 가능합니다.")
    private String name; // 가게명
    @NotBlank(message = "주소를 확인해주세요.")
    @Size(max = 40, message = "주소 최대 길이는 40자 입니다.")
    private String address; // 주소
    @NotBlank(message = "가게 연락처를 확인해주세요.")
    @Pattern(regexp = "^(\\d{3})-(\\d{3,4})-(\\d{4})$",
            message = "가게 연락처는 000-000(0)-0000 형식이어야 합니다.")
    private String contact; // 가게 연락처
    private String description; // 기타 설명
    private String introduction; // 프로필 설명
}
