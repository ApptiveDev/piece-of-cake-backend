package apptive.com.store.cake.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CakeRequest {

    private Long storeId;
    private String name; // 케이크명
    private String description; // 케이크 이름
    private int price; // 가격
}
