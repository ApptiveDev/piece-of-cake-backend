package apptive.com.member.users.model.response;

import apptive.com.common.auth.RoleName;
import apptive.com.member.users.model.Member;
import lombok.Builder;

@Builder
public record MemberMyPageResponse(
        Long userId,
        String name,
        String phoneNum,
        String email,
        String address
) {

    public static MemberMyPageResponse of(Member member) {

        return builder()
                .userId(member.getId())
                .name(member.getName())
                .phoneNum(member.getPhoneNum())
                .email(member.getEmail())
                .address(member.getAddress())
                .build();
    }
}
