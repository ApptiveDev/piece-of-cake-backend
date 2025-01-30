package apptive.com.member.order.service;

import apptive.com.member.order.model.request.OrderRequest;
import apptive.com.member.order.model.response.OrderListResponse;
import apptive.com.member.order.model.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    // 주문 생성
    Long save(Long cakeId, OrderRequest orderRequest);

    // 장바구니 및 주문내역
    Page<OrderListResponse> findAll(Long memberId, String paymentStatus, Pageable pageable);
    OrderResponse findDetail(Long orderId);

    // 주문 삭제
    Long delete(Long memberId, Long orderId);
}
