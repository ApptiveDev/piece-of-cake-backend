package apptive.pieceOfCake.users.model.response;

import apptive.pieceOfCake.auth.RoleName;
import apptive.pieceOfCake.users.model.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record MemberMyPageResponse(
        Long userId,
        String name,
        String phoneNum,
        String email,
        String address,
        String loginPwd,
        boolean agreementOfMarketing,
        RoleName roleName
) {

    public static MemberMyPageResponse of(Member member) {

        return MemberMyPageResponse.builder()
                .userId(member.getId())
                .name(member.getName())
                .phoneNum(member.getPhoneNum())
                .email(member.getEmail())
                .address(member.getAddress())
                .loginPwd(member.getLoginPwd())
                .agreementOfMarketing(member.isAgreementOfMarketing())
                .roleName(member.getRoleName())
                .build();
    }
}
