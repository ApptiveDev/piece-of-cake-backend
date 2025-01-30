package apptive.com.member.order.controller;

import apptive.com.member.order.model.request.OrderRequest;
import apptive.com.member.order.model.response.OrderListResponse;
import apptive.com.member.order.model.response.OrderResponse;
import apptive.com.member.order.service.OrderService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderControllerImpl implements OrderController{

    private final OrderService orderService;

    @Override
    @PostMapping("/{cakeId}")
    public ResponseEntity<Long> save(@PathVariable("cakeId") Long cakeId,
                                     @RequestBody @Valid OrderRequest orderRequest) {

        Long orderId = orderService.save(cakeId, orderRequest);

        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/{memberId}")
    public ResponseEntity<Page<OrderListResponse>> findAll(@PathVariable("memberId") Long memberId,
                                                       @RequestParam("payment") String paymentStatus,
                                                       Pageable pageable) {

        Page<OrderListResponse> orderResponses = orderService.findAll(memberId, paymentStatus, pageable);
        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    }

    @Override
    @GetMapping("/detail/{orderId}")
    public ResponseEntity<OrderResponse> findDetail(@PathVariable("orderId") Long orderId) {

        OrderResponse orderResponse = orderService.findDetail(orderId);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }


    @Override
    @DeleteMapping("/{memberId}/{orderId}")
    public ResponseEntity<Long> delete(@PathVariable("memberId") Long memberId, @PathVariable("orderId") Long orderId) {

        Long order_id = orderService.delete(memberId, orderId);

        return new ResponseEntity<>(order_id, HttpStatus.OK);
    }
}
