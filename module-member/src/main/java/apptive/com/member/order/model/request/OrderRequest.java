package apptive.com.member.order.model.request;

import apptive.com.member.order.model.dto.OrderOptionDto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderRequest {

    @NotNull(message = "케이크 아이디를 입력해주세요.")
    private Long cakeId; // 케이크 아이디
    @NotNull(message = "가게 아이디를 입력해주세요.")
    private Long storeId; // 가게 아이디
    @NotNull(message = "유저 아이디를 입력해주세요.")
    private Long memberId; // 유저 아이디

    private List<OrderOptionDto> options; // 옵션 리스트
    @Size(min = 0, max = 300, message = "300자 이하로 작성해주세요.")
    private String memo; // 주문 메모
    @NotNull(message = "가격을 입력해주세요.")
    @Min(value = 0, message = "총 가격 최소 비용은 0 원입니다.")
    @Max(value = 999999, message = "총 가격 최대 비용은 999999 원입니다.")
    private int totalPrice; // 총 가격
    @NotBlank(message = "픽업 시간을 입력해주세요.")
    private String pickUpTime; // 픽업 시간
    @NotBlank(message = "결제 여부를 확인해주세요.")
    private String paymentStatus; // 결제 여부

    // 결제 정보가 있는 경우
    private String cardNum; // 카드 번호
    private String expirationDate; // 유효기간
    private String password; // 비밀번호
    private String cvcCode; // 보안코드
}
