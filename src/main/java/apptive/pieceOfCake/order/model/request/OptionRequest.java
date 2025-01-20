package apptive.pieceOfCake.order.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OptionRequest {

    private String option; // 선택한 옵션
    private int price; // 가격

}
