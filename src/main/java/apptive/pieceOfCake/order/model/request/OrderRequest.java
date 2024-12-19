package apptive.pieceOfCake.order.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderRequest {

    private Long cakeId; // 케이크 아이디
    private Long storeId; // 가게 아이디
    private Long memberId; // 유저 아이디

    private List<OptionRequest> options; // 옵션 리스트
    private String memo; // 주문 메모
    private String pickUpTime; // 픽업 시간
}
