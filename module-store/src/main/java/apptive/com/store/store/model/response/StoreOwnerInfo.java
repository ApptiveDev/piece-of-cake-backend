package apptive.com.store.store.model.response;

import apptive.com.store.store.model.Store;
import lombok.Builder;

@Builder
public record StoreOwnerInfo(Long storedId,
                             String loginId,
                             String loginPwd) {

    public static StoreOwnerInfo of(Store store) {

        return StoreOwnerInfo.builder()
                .storedId(store.getId())
                .loginId(store.getLoginId())
                .loginPwd(store.getLoginPwd())
                .build();
    }
}
