package apptive.pieceOfCake.order.service;

import apptive.pieceOfCake.order.model.request.OrderRequest;
import apptive.pieceOfCake.order.model.response.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    // 주문 생성
    Long save(OrderRequest orderRequest);

    // 주문 리스트 (상세보기에 들어갈 내용 다 같이 전달)
    List<OrderResponse> findAll(Long memberId);

    // 주문 삭제
    Long delete(Long memberId, Long orderId);
}
