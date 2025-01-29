package apptive.com.member.auth.login.domain.response;

import lombok.Builder;

@Builder
public record OwnerLoginResponse(String accessToken) {
}
