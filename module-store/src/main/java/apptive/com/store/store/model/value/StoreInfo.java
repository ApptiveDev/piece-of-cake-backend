package apptive.com.store.store.model.value;

import apptive.com.store.store.model.request.StoreRegistrationRequest;
import apptive.com.store.store.model.request.StoreUpdateRequest;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Embeddable
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class StoreInfo {

    private String storeName; // 가게명
    private String address; // 주소
    private double latitude; // 위도
    private double longitude; // 경도
    private String contact; // 가게 연락처
    private String etcStoreInfo; // 기타 가게 정보
    private String snsLink; // SNS 링크

    public void saveFrom(StoreRegistrationRequest dto) {
        this.storeName = dto.getStoreName();
        this.address = dto.getAddress();
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        this.contact = dto.getContact();
        this.etcStoreInfo = dto.getEtcStoreInfo();
        this.snsLink = dto.getSnsLink();
    }

    public void updateForm(StoreUpdateRequest dto) {
        this.etcStoreInfo = dto.getEtcStoreInfo();
        this.snsLink = dto.getSnsLink();
    }
}
