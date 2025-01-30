package apptive.com.member.order.model;

import apptive.com.common.base.BaseEntity;
import apptive.com.member.order.model.dto.OrderOption;
import apptive.com.member.order.model.payment.PaymentInfo;
import apptive.com.member.order.model.payment.PaymentStatus;
import apptive.com.member.users.model.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")
public class OrderInfo extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    private Long cakeId;
    private Long storeId;

    @OneToMany(mappedBy = "orderInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderOption> options = new ArrayList<>();

    private String memo; // 주문 메모

    @Column(nullable = false)
    private int totalPrice; // 총 가격
    @Column(nullable = false)
    private String pickUpTime; // 픽업 시간

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // 결제 여부
    @Embedded
    private PaymentInfo paymentInfo; // 결제 정보
}
