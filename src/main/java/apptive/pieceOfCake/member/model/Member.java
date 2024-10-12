package apptive.pieceOfCake.member.model;

import apptive.pieceOfCake.auth.Role;
import apptive.pieceOfCake.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@Where(clause = "is_deleted = 0")
public class Member extends BaseEntity {

    // 일반 로그인 시 사용
    private String loginId; // 로그인 아이디
    private String loginPwd; // 로그인 비밀번호

    // 소셜 로그인 시 사용
    private String provider; // kakao, naver
    private String providerId; // 해당 OAuth 의 key(id)

    @Column(nullable = false)
    private String phoneNum; // 전화번호
    @Column(nullable = false)
    private String email; // 이메일
    @Column(nullable = false)
    private String name; // 이름
    @Enumerated(EnumType.STRING)
    private Role role; //ADMIN, STORE, USER
}
