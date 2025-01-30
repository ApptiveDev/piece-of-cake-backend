package apptive.com.store.store.model;

import apptive.com.common.auth.RoleName;
import apptive.com.common.base.BaseEntity;
import apptive.com.store.store.model.request.StoreRegistrationRequest;
import apptive.com.store.store.model.value.BankInfo;
import apptive.com.store.store.model.value.StoreInfo;
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
    private String phoneNum; // 대표 연락

    @Embedded
    private StoreInfo storeInfo; // 가게 정보

    @Embedded
    private BankInfo bankInfo; // 정산 정보

    private String bannerImage; // 프로필 이미지
    private String logoImage; // 로고 이미지

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RoleName roleName;
}
