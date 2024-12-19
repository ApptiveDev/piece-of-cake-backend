package apptive.pieceOfCake.order.controller;

import apptive.pieceOfCake.order.model.request.OrderRequest;
import apptive.pieceOfCake.order.model.response.OrderResponse;
import apptive.pieceOfCake.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderControllerImpl implements OrderController{

    private final OrderService orderService;

    @Override
    @PostMapping
    public ResponseEntity<Long> save(@RequestBody OrderRequest orderRequest) {

        Long orderId = orderService.save(orderRequest);

        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/{memberId}")
    public ResponseEntity<List<OrderResponse>> findAll(@PathVariable("memberId") Long memberId) {

        List<OrderResponse> orderResponses = orderService.findAll(memberId);
        return new ResponseEntity<>(orderResponses, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{memberId}/{orderId}")
    public ResponseEntity<Long> delete(@PathVariable("memberId") Long memberId, @PathVariable("orderId") Long orderId) {

        Long order_id = orderService.delete(memberId, orderId);

        return new ResponseEntity<>(order_id, HttpStatus.OK);
    }
}
