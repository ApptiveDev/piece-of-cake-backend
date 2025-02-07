package apptive.com.member.order.model.response;

import apptive.com.member.order.model.OrderInfo;
import lombok.*;

@Builder @Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderListResponse {

    private Long orderId;
    private String storeName;
    private String storeImage;

    private String cakeName;
    private String cakeImage;
    private int quantity;
    private String pickUpTime;
    private int total;
}
