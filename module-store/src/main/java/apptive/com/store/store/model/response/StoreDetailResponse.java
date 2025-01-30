package apptive.com.store.store.model.response;

import apptive.com.store.store.model.Store;
import lombok.*;

@Builder
public record StoreDetailResponse (Long storeId, // 가게 아이디
                                   String name, // 가게 이름
                                   String address, // 주소
                                   String contact, // 가게 연락처
                                   String etcStoreInfo, // 기타 가게 정보
                                   String snsLink, // SNS 링크
                                   String bannerImage, // 배너 이미지
                                   String logoImage  // 로고 이미지
                                   ) {

    public static StoreDetailResponse of(Store store) {

        return StoreDetailResponse.builder()
                .storeId(store.getId())
                .name(store.getStoreInfo().getStoreName())
                .address(store.getStoreInfo().getAddress())
                .contact(store.getStoreInfo().getContact())
                .etcStoreInfo(store.getStoreInfo().getEtcStoreInfo())
                .snsLink(store.getStoreInfo().getSnsLink())
                .bannerImage(store.getBannerImage())
                .logoImage(store.getLogoImage())
                .build();
    }
}
