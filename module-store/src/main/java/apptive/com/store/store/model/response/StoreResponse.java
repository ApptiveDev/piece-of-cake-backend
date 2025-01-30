package apptive.com.store.store.model.response;

import apptive.com.store.store.model.Store;
import lombok.Builder;

import java.util.List;

@Builder
public record StoreResponse(Long storeId,
                            String name,
                            String logoImage,
                            double longitude,
                            double latitude,
                            double distance,
                            List<String> cakeImages) {

    public static StoreResponse of(Store store, List<String> cakeImages, double distance) {
        return StoreResponse.builder()
                .storeId(store.getId())
                .name(store.getStoreInfo().getStoreName())
                .logoImage(store.getLogoImage())
                .longitude(store.getStoreInfo().getLongitude())
                .latitude(store.getStoreInfo().getLatitude())
                .cakeImages(cakeImages)
                .distance(distance)
                .build();
    }
}
