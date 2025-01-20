package apptive.pieceOfCake.cake.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CakeListResponse {

    private Long cakeId;

    private String cakeName;
    private String description;
    private String cakeImage;
    private int price;
}
