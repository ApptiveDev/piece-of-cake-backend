package apptive.pieceOfCake.order.controller;

import apptive.pieceOfCake.order.model.request.OrderRequest;
import apptive.pieceOfCake.order.model.response.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface OrderController {

    ResponseEntity<Long> save(OrderRequest orderRequest); // 주문 생성
    ResponseEntity<List<OrderResponse>> findAll(@PathVariable Long memberId); // 주문 리스트
    ResponseEntity<Long> delete(@PathVariable Long memberId, @PathVariable Long orderId); // 주문 삭제
}
