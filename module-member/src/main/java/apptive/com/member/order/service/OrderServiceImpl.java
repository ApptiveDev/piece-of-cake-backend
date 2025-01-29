package apptive.com.member.order.service;

import apptive.com.member.order.exception.OrderException;
import apptive.com.member.order.exception.OrderExceptionType;
import apptive.com.member.order.model.OrderInfo;
import apptive.com.member.order.model.PaymentStatus;
import apptive.com.member.order.model.dto.Option;
import apptive.com.member.order.model.dto.OptionResponse;
import apptive.com.member.order.model.request.OrderRequest;
import apptive.com.member.order.model.response.OrderResponse;
import apptive.com.member.order.repository.OptionRepository;
import apptive.com.member.order.repository.OrderRepository;
import apptive.com.member.users.exception.MemberException;
import apptive.com.member.users.model.Member;
import apptive.com.member.users.repository.MemberRepository;
import apptive.com.store.cake.exception.CakeException;
import apptive.com.store.cake.model.Cake;
import apptive.com.store.cake.repository.CakeRepository;
import apptive.com.store.store.exception.StoreException;
import apptive.com.store.store.model.Store;
import apptive.com.store.store.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static apptive.com.member.users.exception.MemberExceptionType.NOT_FOUND_MEMBER;
import static apptive.com.store.cake.exception.CakeExceptionType.NOT_FOUND_CAKE;
import static apptive.com.store.store.exception.StoreExceptionType.NOT_FOUND_STORE;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OptionRepository optionRepository;
    private final CakeRepository cakeRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long save(OrderRequest orderRequest) {

        // 데이터 유효성 확인
        if (cakeRepository.findById(orderRequest.getCakeId()).isEmpty()) {
            throw new CakeException(NOT_FOUND_CAKE);
        } else if (storeRepository.findById(orderRequest.getStoreId()).isEmpty()) {
            throw new StoreException(NOT_FOUND_STORE);
        } else if (memberRepository.findById(orderRequest.getMemberId()).isEmpty()) {
            throw new MemberException(NOT_FOUND_MEMBER);
        }

        PaymentStatus paymentStatus;
        try {
            paymentStatus = PaymentStatus.valueOf(orderRequest.getPaymentStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new OrderException(OrderExceptionType.CHECK_PAYMENT_STATUS);
        }

        // null 값 최소화
        orderRequest.initialize();

        // OrderInfo 객체 생성
        OrderInfo orderInfo = OrderInfo.builder()
                .storeId(orderRequest.getStoreId())
                .cakeId(orderRequest.getCakeId())
                .memberId(orderRequest.getMemberId())
                .memo(orderRequest.getMemo())
                .totalPrice(orderRequest.getTotalPrice())
                .pickUpTime(orderRequest.getPickUpTime())
                .paymentStatus(paymentStatus)
                .build();

        // 옵션 리스트 생성 및 Order와 연결
        List<Option> options = orderRequest.getOptions().stream()
                .map(optionRequest -> {
                    Option option = new Option();
                    option.setOption(optionRequest.getOption());
                    option.setPrice(optionRequest.getPrice());
                    option.setOrderInfo(orderInfo); // Order와 연결
                    return option;
                }).toList();

        // Order에 옵션 리스트 설정
        orderInfo.setOptions(options);

        // OrderInfo 저장
        OrderInfo savedOrderInfo = orderRepository.save(orderInfo);

        // 옵션들도 함께 저장
        optionRepository.saveAll(options);

        return savedOrderInfo.getId(); // 저장된 Order의 ID 반환
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> findAll(Long memberId, String paymentStatus, Pageable pageable) {

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

            OrderResponse orderResponse = OrderResponse.builder()
                    .orderId(orderInfo.getId())
                    .storeName(store.getName())
                    .pickUpTime(orderInfo.getPickUpTime())
                    .cakeName(cake.getName())
                    .cakeImage(cake.getCakeImage())
                    .quantity(1)
                    .memo(orderInfo.getMemo())
                    .build();

            // 옵션 리스트 변환
            List<OptionResponse> optionResponses = orderInfo.getOptions().stream()
                    .map(option -> {
                        OptionResponse optionResponse = new OptionResponse();
                        optionResponse.setOption(option.getOption());
                        optionResponse.setPrice(option.getPrice());
                        return optionResponse;
                    })
                    .collect(Collectors.toList());

            orderResponse.setOptions(optionResponses);
            return orderResponse;
        });
    }

    @Override
    @Transactional
    public Long delete(Long memberId, Long orderId) {
        // 주문 찾기
        OrderInfo orderInfo = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderExceptionType.NOT_FOUND_ORDER));

        if (Objects.equals(orderInfo.getMemberId(), memberId)) {
            // 주문 삭제 (옵션도 함께 삭제됨)
            orderRepository.delete(orderInfo);
        } else {
            throw new OrderException(OrderExceptionType.NOT_MATCH_MEMBER);
        }

        return orderId; // 삭제된 주문의 ID 반환
    }
}
