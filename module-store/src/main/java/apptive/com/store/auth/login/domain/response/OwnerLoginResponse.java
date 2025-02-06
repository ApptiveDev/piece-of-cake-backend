package apptive.com.store.auth.login.domain.response;

import lombok.Builder;

@Builder
public record OwnerLoginResponse(Long storeId,
                                 boolean isStoreApplied,
                                 String accessToken) {
}
