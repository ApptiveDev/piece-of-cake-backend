package apptive.com.member.order.service;

import apptive.com.common.cake.exception.CakeException;
import apptive.com.member.order.exception.OrderException;
import apptive.com.member.order.exception.OrderExceptionType;
import apptive.com.member.order.model.OrderInfo;
import apptive.com.member.order.model.dto.OrderOption;
import apptive.com.member.order.model.dto.OrderOptionDto;
import apptive.com.member.order.model.payment.PaymentInfo;
import apptive.com.member.order.model.payment.PaymentStatus;
import apptive.com.member.order.model.request.OrderRequest;
import apptive.com.member.order.model.response.OrderListResponse;
import apptive.com.member.order.model.response.OrderResponse;
import apptive.com.member.order.repository.OrderRepository;
import apptive.com.member.users.exception.MemberException;
import apptive.com.member.users.model.Member;
import apptive.com.member.users.repository.MemberRepository;
import apptive.com.store.cake.model.Cake;
import apptive.com.store.cake.repository.CakeRepository;
import apptive.com.common.store.exception.StoreException;
import apptive.com.store.store.model.Store;
import apptive.com.store.store.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;

import static apptive.com.common.cake.exception.CakeExceptionType.NOT_FOUND_CAKE;
import static apptive.com.member.order.exception.OrderExceptionType.NOT_FOUND_ORDER;
import static apptive.com.member.users.exception.MemberExceptionType.NOT_FOUND_MEMBER;
import static apptive.com.common.store.exception.StoreExceptionType.NOT_FOUND_STORE;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final CakeRepository cakeRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long save(Long cakeId, OrderRequest orderRequest) {

        // 데이터 유효성 확인
        if (cakeRepository.findById(cakeId).isEmpty()) {
            throw new CakeException(NOT_FOUND_CAKE);
        }
        Member member = memberRepository.findById(orderRequest.getMemberId())
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        PaymentStatus paymentStatus;
        try {
            paymentStatus = PaymentStatus.valueOf(orderRequest.getPaymentStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new OrderException(OrderExceptionType.CHECK_PAYMENT_STATUS);
        }

        // OrderInfo 객체 생성
        OrderInfo orderInfo = OrderInfo.builder()
                .member(member)
                .cakeId(orderRequest.getCakeId())
                .storeId(orderRequest.getStoreId())
                .options(new ArrayList<>())
                .memo(orderRequest.getMemo())
                .totalPrice(orderRequest.getTotalPrice())
                .pickUpTime(orderRequest.getPickUpTime())
                .paymentStatus(paymentStatus)
                .build();

        // 옵션 리스트 생성 및 Order와 연결
        for (OrderOptionDto optionDto : orderRequest.getOptions()) {
            OrderOption option = OrderOption.builder()
                    .orderInfo(orderInfo)
                    .type(optionDto.getType())
                    .value(optionDto.getValue())
                    .price(optionDto.getPrice())
                    .build();
            orderInfo.getOptions().add(option);
        }

        orderInfo.setPaymentInfo(new PaymentInfo());
        // 결제 정보가 있는 경우
        if (paymentStatus == PaymentStatus.PURCHASE) {
            orderInfo.getPaymentInfo().saveFrom(orderRequest);
        }

        orderRepository.save(orderInfo);

        return orderInfo.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderListResponse> findAll(Long memberId, String paymentStatus, Pageable pageable) {

        // 존재하는 멤버인지 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        PaymentStatus payment_status;
        try {
            payment_status = PaymentStatus.valueOf(paymentStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new OrderException(OrderExceptionType.CHECK_PAYMENT_STATUS);
        }

        Page<OrderInfo> orderInfos = orderRepository.findByMemberIdAndPaymentStatusOrderByCreateAt(member.getId(), payment_status, pageable);

        return orderInfos.map(orderInfo -> {

            Cake cake = cakeRepository.findById(orderInfo.getCakeId())
                    .orElseThrow(() -> new CakeException(NOT_FOUND_CAKE));
            Store store = storeRepository.findById(orderInfo.getStoreId())
                    .orElseThrow(() -> new StoreException(NOT_FOUND_STORE));

            OrderListResponse orderResponse = OrderListResponse.builder()
                    .orderId(orderInfo.getId())
                    .storeName(store.getStoreInfo().getStoreName())
                    .storeImage(store.getLogoImage())
                    .pickUpTime(orderInfo.getPickUpTime())
                    .cakeName(cake.getName())
                    .cakeImage(cake.getCakeImage())
                    .total(orderInfo.getTotalPrice())
                    .quantity(1)
                    .build();

            return orderResponse;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse findDetail(Long orderId) {

        return orderRepository.findById(orderId)
                .map(OrderResponse::of)
                .orElseThrow(() -> new OrderException(NOT_FOUND_ORDER));
    }

    @Override
    @Transactional
    public Long delete(Long memberId, Long orderId) {
        // 주문 찾기
        OrderInfo orderInfo = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(NOT_FOUND_ORDER));

        if (Objects.equals(orderInfo.getMember().getId(), memberId)) {
            // 주문 삭제 (옵션도 함께 삭제됨)
            orderRepository.delete(orderInfo);
        } else {
            throw new OrderException(OrderExceptionType.NOT_MATCH_MEMBER);
        }

        return orderId; // 삭제된 주문의 ID 반환
    }
}
