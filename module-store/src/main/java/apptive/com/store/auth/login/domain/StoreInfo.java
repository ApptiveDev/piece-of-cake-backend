package apptive.com.store.auth.login.domain;

import apptive.com.common.auth.RoleName;
import apptive.com.store.store.model.Store;
import lombok.Builder;

@Builder
public record StoreInfo(Long storeId,
                        String loginId,
                        String loginPwd,
                        RoleName roleName) {

    public static StoreInfo of(Store store) {

        return builder()
                .storeId(store.getId())
                .loginId(store.getLoginId())
                .loginPwd(store.getLoginPwd())
                .roleName(store.getRoleName())
                .build();
    }
}
