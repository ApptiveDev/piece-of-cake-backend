package apptive.pieceOfCake.order.model.request;

import apptive.pieceOfCake.order.exception.OrderException;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static apptive.pieceOfCake.order.exception.OrderExceptionType.*;

@Getter @Setter
public class OrderRequest {

    @NotBlank(message = "케이크 아이디를 입력해주세요.")
    private Long cakeId; // 케이크 아이디
    @NotBlank(message = "가게 아이디를 입력해주세요.")
    private Long storeId; // 가게 아이디
    @NotBlank(message = "유저 아이디를 입력해주세요.")
    private Long memberId; // 유저 아이디

    private List<OptionRequest> options; // 옵션 리스트
    @Size(min = 0, max = 300, message = "300자 이하로 작성해주세요.")
    private String memo; // 주문 메모
    @NotBlank(message = "케이크 아이디를 입력해주세요.")
    @Min(value = 0, message = "총 가격 최소 비용은 0 원입니다.")
    @Max(value = 999999, message = "총 가격 최대 비용은 999999 원입니다.")
    private int totalPrice; // 총 가격
    @NotBlank(message = "픽업 시간을 입력해주세요.")
    private String pickUpTime; // 픽업 시간
    @NotBlank(message = "결제 여부를 확인해주세요.")
    private String paymentStatus; // 결제 여부

    public void initialize() {
        if (memo == null) memo = "";
        if (totalPrice == 0) {
            throw new OrderException(CHECK_TOTAL_PRICE);
        }
        if (pickUpTime == null) {
            throw new OrderException(CHECK_PICK_UP_TIME);
        }
        if (paymentStatus == null) {
            throw new OrderException(CHECK_PAYMENT_STATUS);
        }
    }
}
