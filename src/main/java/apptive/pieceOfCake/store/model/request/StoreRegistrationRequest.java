package apptive.pieceOfCake.store.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreRegistrationRequest {

    private String name; // 가게명
    private String address; // 주소
    private double latitude; // 위도
    private double longitude; // 경도
    private String contact; // 가게 연락처
    private String phoneNum; // 대표 연락
    private String sLink; // SNS 링크
    private String profileIntroduction; // 프로필 설명
}