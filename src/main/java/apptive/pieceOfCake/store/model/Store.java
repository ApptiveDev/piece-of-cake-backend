package apptive.pieceOfCake.store.model;

import apptive.pieceOfCake.auth.RoleName;
import apptive.pieceOfCake.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")
public class Store extends BaseEntity {

    /**
     * storeId == ownerId
     * 같은 엔티티로 엮어서 관리함
     */

    private String loginId; // 로그인 아이디
    private String loginPwd; // 로그인 비밀번호

    private String name; // 가게명
    private String address; // 주소
    private double latitude; // 위도
    private double longitude; // 경도
    private String contact; // 가게 연락처
    private String phoneNum; // 대표 연락
    private String businessHours; // 영업시간
    private String closedDays; // 휴무일
    private String snsLink; // SNS 링크
    private String profileImage; // 프로필 이미지
    private String logoImage; // 로고 이미지
    private String profileIntroduction; // 프로필 설명

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RoleName roleName;
}
