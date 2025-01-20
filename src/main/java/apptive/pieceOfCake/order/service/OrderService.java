package apptive.pieceOfCake.order.service;

import apptive.pieceOfCake.order.model.request.OrderRequest;
import apptive.pieceOfCake.order.model.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    // 주문 생성
    Long save(OrderRequest orderRequest);

    // 장바구니 (상세보기에 들어갈 내용 다 같이 전달)
    Page<OrderResponse> findAll(Long memberId, String paymentStatus, Pageable pageable);

    // 주문 삭제
    Long delete(Long memberId, Long orderId);
}
