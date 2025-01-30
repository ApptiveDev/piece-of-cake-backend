package apptive.com.member.order.controller;

import apptive.com.member.order.model.request.OrderRequest;
import apptive.com.member.order.model.response.OrderListResponse;
import apptive.com.member.order.model.response.OrderResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface OrderController {

    ResponseEntity<Long> save(@PathParam("cakeId") Long cakeId,
                              @RequestBody OrderRequest orderRequest); // 주문 생성
    ResponseEntity<Page<OrderListResponse>> findAll(@PathVariable Long memberId,
                                                    @RequestParam String paymentStatus,
                                                    @PageableDefault(size = 5) Pageable pageable); // 주문 리스트
    ResponseEntity<OrderResponse> findDetail(@PathVariable Long orderId); // 주문 상세보기
    ResponseEntity<Long> delete(@PathVariable Long memberId, @PathVariable Long orderId); // 주문 삭제
}
