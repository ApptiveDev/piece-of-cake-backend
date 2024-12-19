package apptive.pieceOfCake.order.repository;

import apptive.pieceOfCake.order.model.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderInfo, Long> {
    List<OrderInfo> findByMemberId(Long memberId); // 회원 ID로 주문 조회
}
