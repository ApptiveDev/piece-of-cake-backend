package apptive.pieceOfCake.users.model;

import apptive.pieceOfCake.auth.Role;
import apptive.pieceOfCake.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")
public class Member extends BaseEntity {

    // 일반 로그인 시 사용
    private String email; // 로그인 아이디 (이메일)
    private String loginPwd; // 로그인 비밀번호

    // 소셜 로그인 시 사용
    //private String provider; // kakao, naver
    //private String providerId; // 해당 OAuth 의 key(id)

    private String name; // 이름
    private String phoneNum; // 전화번호
    private String address; // 이메일

    private double latitude; // 위도
    private double longitude; // 경도

    private boolean agreementOfMarketing; // 마케팅 수신 동의 여부

    @Enumerated(EnumType.STRING)
    private Role role; //ADMIN, STORE, USER
}
