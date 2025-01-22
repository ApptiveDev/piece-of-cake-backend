package apptive.pieceOfCake.auth.login.domain;

import apptive.pieceOfCake.auth.RoleName;
import apptive.pieceOfCake.store.model.Store;
import apptive.pieceOfCake.users.model.Member;
import lombok.Builder;

@Builder
public record UserInfo(Long userId,
                       String loginId,
                       String loginPwd,
                       RoleName roleName) {

    public static UserInfo of(Member member) {

        return UserInfo.builder()
                .userId(member.getId())
                .loginId(member.getEmail())
                .loginPwd(member.getLoginPwd())
                .roleName(member.getRoleName())
                .build();
    }

    public static UserInfo of(Store store) {

        return UserInfo.builder()
                .userId(store.getId())
                .loginId(store.getLoginId())
                .loginPwd(store.getLoginPwd())
                .roleName(store.getRoleName())
                .build();
    }
}
