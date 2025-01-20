package apptive.pieceOfCake.store.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreCakeResponse {

    Long cakeId;
    Long storeId;

    String name; // 케이크명
    String description; // 케이크 이름
    String cakeImage; // 케이크 대표 이미지

    public StoreCakeResponse(Long cakeId, Long storeId, String name, String description, String cakeImage) {
        this.cakeId = cakeId;
        this.storeId = storeId;
        this.name = name;
        this.description = description;
        this.cakeImage = cakeImage;
    }
}
