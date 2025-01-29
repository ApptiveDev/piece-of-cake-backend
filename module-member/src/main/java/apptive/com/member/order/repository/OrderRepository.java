package apptive.com.member.order.repository;

import apptive.com.member.order.model.OrderInfo;
import apptive.com.member.order.model.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderInfo, Long> {

    Page<OrderInfo> findByMemberIdAndPaymentStatusOrderByCreateAt(Long memberId, PaymentStatus paymentStatus, Pageable pageable); // 회원 ID로 주문 조회

}
