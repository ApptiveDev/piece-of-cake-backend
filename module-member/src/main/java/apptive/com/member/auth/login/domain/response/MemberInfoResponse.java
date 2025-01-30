package apptive.com.member.auth.login.domain.response;

import apptive.com.common.auth.RoleName;
import apptive.com.member.users.model.Member;
import lombok.Builder;

@Builder
public record MemberInfoResponse(Long userId,
                                String loginId,
                                String loginPwd,
                                RoleName roleName) {

    public static MemberInfoResponse of(Member member) {
        return builder()
                .userId(member.getId())
                .loginId(member.getEmail())
                .loginPwd(member.getLoginPwd())
                .roleName(member.getRoleName())
                .build();
    }
}
