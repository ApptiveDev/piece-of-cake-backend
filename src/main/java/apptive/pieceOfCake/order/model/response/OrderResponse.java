package apptive.pieceOfCake.order.model.response;

import apptive.pieceOfCake.order.model.dto.OptionResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Builder
public class OrderResponse {

    private Long orderId; // 주문 ID

    private String storeName; // 가게 이름
    private String pickUpTime;
    private String cakeName; // 케이크 이름인
    private String cakeImage; // 케이크 이미지
    private int quantity; // 케이크 개수
    private List<OptionResponse> options; // 옵션 리스트
    private String memo; //배송 메모
}
