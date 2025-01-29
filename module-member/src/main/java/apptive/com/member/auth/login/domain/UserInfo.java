package apptive.com.member.auth.login.domain;

import apptive.com.common.auth.RoleName;
import apptive.com.member.users.model.Member;
import lombok.Builder;

@Builder
public record UserInfo(Long userId,
                       String loginId,
                       String loginPwd,
                       RoleName roleName) {

    public static UserInfo of(Member member) {

        return builder()
                .userId(member.getId())
                .loginId(member.getEmail())
                .loginPwd(member.getLoginPwd())
                .roleName(member.getRoleName())
                .build();
    }
}
