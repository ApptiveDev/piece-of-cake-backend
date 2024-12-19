package apptive.pieceOfCake.order.service;

import apptive.pieceOfCake.cake.exception.CakeException;
import apptive.pieceOfCake.cake.model.Cake;
import apptive.pieceOfCake.cake.repository.CakeRepository;
import apptive.pieceOfCake.order.exception.OrderException;
import apptive.pieceOfCake.order.model.OrderInfo;
import apptive.pieceOfCake.order.model.dto.Option;
import apptive.pieceOfCake.order.model.dto.OptionResponse;
import apptive.pieceOfCake.order.model.request.OrderRequest;
import apptive.pieceOfCake.order.model.response.OrderResponse;
import apptive.pieceOfCake.order.repository.OptionRepository;
import apptive.pieceOfCake.order.repository.OrderRepository;
import apptive.pieceOfCake.store.exception.StoreException;
import apptive.pieceOfCake.store.model.Store;
import apptive.pieceOfCake.store.repository.StoreRepository;
import apptive.pieceOfCake.users.exception.MemberException;
import apptive.pieceOfCake.users.model.Member;
import apptive.pieceOfCake.users.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static apptive.pieceOfCake.cake.exception.CakeExceptionType.NOT_FOUND_CAKE;
import static apptive.pieceOfCake.order.exception.OrderExceptionType.NOT_FOUND_ORDER;
import static apptive.pieceOfCake.order.exception.OrderExceptionType.NOT_MATCH_MEMBER;
import static apptive.pieceOfCake.store.exception.StoreExceptionType.NOT_FOUND_STORE;
import static apptive.pieceOfCake.users.exception.MemberExceptionType.NOT_FOUND_MEMBER;

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

        // OrderInfo 객체 생성
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCakeId(orderRequest.getCakeId());
        orderInfo.setStoreId(orderRequest.getStoreId());
        orderInfo.setMemberId(orderRequest.getMemberId());
        orderInfo.setMemo(orderRequest.getMemo());
        orderInfo.setPickUpTime(orderRequest.getPickUpTime());

        // 옵션 리스트 생성 및 Order와 연결
        List<Option> options = orderRequest.getOptions().stream()
                .map(optionRequest -> {
                    Option option = new Option();
                    option.setOption(optionRequest.getOption());
                    option.setPrice(optionRequest.getPrice());
                    option.setOrderInfo(orderInfo); // Order와 연결
                    return option;
                })
                .toList();

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
    public List<OrderResponse> findAll(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER));

        List<OrderInfo> orderInfos = orderRepository.findByMemberId(memberId); // memberId로 주문 조회

        return orderInfos.stream().map(orderInfo -> {

            Cake cake = cakeRepository.findById(orderInfo.getCakeId())
                    .orElseThrow(() -> new CakeException(NOT_FOUND_CAKE));
            Store store = storeRepository.findById(orderInfo.getStoreId())
                    .orElseThrow(() -> new StoreException(NOT_FOUND_STORE));

            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderId(orderInfo.getId());
            orderResponse.setStoreName(store.getName()); // 가게 이름 설정
            orderResponse.setCakeName(cake.getName()); // 케이크 이름 설정
            orderResponse.setCakeImage(cake.getCakeImage()); // 케이크 이미지 설정
            orderResponse.setQuantity(orderInfos.size()); // 케이크 개수 설정
            orderResponse.setMemo(orderInfo.getMemo());

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
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long delete(Long memberId, Long orderId) {
        // 주문 찾기
        OrderInfo orderInfo = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(NOT_FOUND_ORDER));

        if (Objects.equals(orderInfo.getMemberId(), memberId)) {
            // 주문 삭제 (옵션도 함께 삭제됨)
            orderRepository.delete(orderInfo);
        } else {
            throw new OrderException(NOT_MATCH_MEMBER);
        }

        return orderId; // 삭제된 주문의 ID 반환
    }
}
