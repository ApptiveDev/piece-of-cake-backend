package apptive.pieceOfCake.order.model;

import apptive.pieceOfCake.base.BaseEntity;
import apptive.pieceOfCake.order.model.dto.Option;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")
public class OrderInfo extends BaseEntity {

    private Long cakeId; // 케이크 아이디
    private Long storeId; // 가게 아이디
    private Long memberId; // 유저 아이디

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<Option> options; // 옵션 리스트

    private String memo; // 주문 메모

    private String pickUpTime; // 픽업 시간
}
