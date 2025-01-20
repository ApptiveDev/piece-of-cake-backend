package apptive.pieceOfCake.store.model.response;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDetailResponse {

    private Long storeId; // 가게 아이디

    private String name; // 가게 이름
    private String address; // 주소
    private String contact; // 가게 연락처
    private String phoneNum; // 대표 연락
    private String businessHours; // 영업시간
    private String closedDays; // 휴무일
    private String snsLink; // SNS 링크
    private String profileIntroduction; // 프로필 설명

    private String profileImage; // 프로필 이미지
    private String logoImage; // 로고 이미지

    public void initialize() {
        if (name == null) name = "";
        if (address == null) address = "";
        if (contact == null) contact = "";
        if (phoneNum == null) phoneNum = "";
        if (businessHours == null) businessHours = "";
        if (closedDays == null) closedDays = "";
        if (snsLink == null) snsLink = "";
        if (profileIntroduction == null) profileIntroduction = "";
        if (profileImage == null) profileImage = ""; // 추가된 초기화
        if (logoImage == null) logoImage = ""; // 추가된 초기화
    }
}
