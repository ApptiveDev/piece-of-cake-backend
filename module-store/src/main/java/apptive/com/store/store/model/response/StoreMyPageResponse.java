package apptive.com.store.store.model.response;

import apptive.com.store.store.model.Store;
import lombok.Builder;

@Builder
public record StoreMyPageResponse(Long storeId,
                                  String storeName,
                                  String bannerImage,
                                  String logoImage,
                                  String etcStoreInfo,
                                  String snsLink
                                  ) {

    public static StoreMyPageResponse of(Store store) {

        return StoreMyPageResponse.builder()
                .storeId(store.getId())
                .storeName(store.getStoreInfo().getStoreName())
                .bannerImage(store.getBannerImage())
                .logoImage(store.getLogoImage())
                .etcStoreInfo(store.getStoreInfo().getEtcStoreInfo())
                .snsLink(store.getStoreInfo().getSnsLink())
                .build();
    }
}
