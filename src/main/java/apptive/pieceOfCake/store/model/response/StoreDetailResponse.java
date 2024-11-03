package apptive.pieceOfCake.store.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreDetailResponse {

    private Long storeId; // 가게 아이디

    private String name; // 가게 이름
    private String address; // 주소
    private String contact; // 가게 연락처
    private String phoneNum; // 대표 연락
    private String sLink; // SNS 링크
    private String description; // 기타 설명
    private String image; // 프로필 이미지
    private String introduction; // 프로필 설명
}
