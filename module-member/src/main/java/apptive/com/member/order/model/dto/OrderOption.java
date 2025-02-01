package apptive.com.member.order.model.dto;

import apptive.com.common.base.BaseEntity;
import apptive.com.member.order.model.OrderInfo;
import apptive.com.common.cake.OptionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderOption extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_info_id", nullable = false)
    private OrderInfo orderInfo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OptionType type; // SIZE, TASTE, CREAM, COLOR, ETC

    @Column(nullable = false)
    private String value; // 옵션 이름

    @Column(nullable = false)
    private int price; // 옵션 가격
}
