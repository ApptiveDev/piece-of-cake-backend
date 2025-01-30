package apptive.com.store.store.model.response;

import apptive.com.store.cake.model.Cake;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreCakeResponse {

    Long cakeId;
    Long storeId;

    String name; // 케이크명
    String description; // 케이크 이름
    String cakeImage; // 케이크 대표 이미지

    public StoreCakeResponse(Cake cake) {
        this.cakeId = cake.getId();
        this.storeId = cake.getStore().getId();
        this.name = cake.getName();
        this.description = cake.getDescription();
        this.cakeImage = cake.getCakeImage();
    }
}
