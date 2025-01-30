package apptive.com.store.store.model;

import apptive.com.common.auth.RoleName;
import apptive.com.common.base.BaseEntity;
import apptive.com.store.store.model.value.BankInfo;
import jakarta.persistence.*;
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

    private String ownerName; // 이름
    private String storeName; // 가게명
    private String address; // 주소
    private double latitude; // 위도
    private double longitude; // 경도
    private String contact; // 가게 연락처
    private String phoneNum; // 대표 연락
    private String etcStoreInfo; // 기타 가게 정보
    private String snsLink; // SNS 링크

    @Embedded
    private BankInfo bankInfo; // 정산 정보

    private String profileImage; // 프로필 이미지
    private String logoImage; // 로고 이미지
    private String profileIntroduction; // 프로필 설명

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RoleName roleName;
}
