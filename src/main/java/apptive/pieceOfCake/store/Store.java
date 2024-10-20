package apptive.pieceOfCake.store;

import apptive.pieceOfCake.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

@Entity
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@Where(clause = "is_deleted = 0")
public class Store extends BaseEntity {

    private String loginId; // 로그인 아이디
    private String loginPwd; // 로그인 비밀번호

    private String name; // 가게명
    private String address; // 주소
    private String latitude; // 위도
    private String longitude; // 경도
    private String contact; // 가게 연락처
    private String phoneNum; // 대표 연락
    private String sLink; // SNS 링크
    private String description; // 기타 설명
    private String image; // 프로필 이미지
    private String introduction; // 프로필 설명
}
