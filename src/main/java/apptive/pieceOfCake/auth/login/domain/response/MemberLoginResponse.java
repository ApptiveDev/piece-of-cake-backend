package apptive.pieceOfCake.auth.login.domain.response;

import lombok.Builder;

@Builder
public record MemberLoginResponse(String accessToken) {
}
