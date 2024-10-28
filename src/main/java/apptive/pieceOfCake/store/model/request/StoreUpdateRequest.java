package apptive.pieceOfCake.store.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreUpdateRequest {

    private String name; // 가게명
    private String address; // 주소
    private String contact; // 가게 연락처
    private String description; // 기타 설명
    private String introduction; // 프로필 설명
}
