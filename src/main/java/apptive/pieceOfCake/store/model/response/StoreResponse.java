package apptive.pieceOfCake.store.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreResponse {

    private Long storeId;

    private String name; // 가게명
    private String address; // 주소
    private String contact; // 가게 연락처
    private String description; // 기타 설명
    private String image; // 프로필 이미지
    private String introduction; // 프로필 설명
}
