package apptive.com.store.store.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreResponse {

    private Long storeId;

    private String name; // 가게명
    private String profileImage; // 프로필 이미지
    private String logoImage; // 로고 이미지
    private double distance; // 가게까지의 거리
    // private List<String> cakeImages; // 제품 이미지
}
