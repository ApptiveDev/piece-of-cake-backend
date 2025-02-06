package apptive.com.member.auth.login.domain.response;

import lombok.Builder;

@Builder
public record MemberLoginResponse(Long userId,
                                  double latitude,
                                  double longitude,
                                  String accessToken) {
}
