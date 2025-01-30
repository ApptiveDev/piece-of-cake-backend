package apptive.com.store.cake.model.response;

import apptive.com.store.cake.model.Cake;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CakeListResponse {

    private Long cakeId;
    private String cakeName;
    private String description;
    private String cakeImage;
    private int price;

    // Cake 엔티티를 받아서 변환하는 생성자
    public CakeListResponse(Cake cake) {
        this.cakeId = cake.getId();
        this.cakeName = cake.getName();
        this.description = cake.getDescription();
        this.cakeImage = cake.getCakeImage();
        this.price = cake.getPrice();
    }
}
