package apptive.com.member.auth.login.domain.response;

import apptive.com.common.auth.RoleName;
import apptive.com.member.users.model.Member;
import lombok.Builder;

@Builder
public record MemberInfoResponse(Long userId,
                                String loginId,
                                String loginPwd,
                                double latitude,
                                double longitude,
                                RoleName roleName) {

    public static MemberInfoResponse of(Member member) {
        return builder()
                .userId(member.getId())
                .loginId(member.getEmail())
                .loginPwd(member.getLoginPwd())
                .latitude(member.getLatitude())
                .longitude(member.getLongitude())
                .roleName(member.getRoleName())
                .build();
    }
}
