package apptive.pieceOfCake.cake.model.response.option;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ColorOptionResponse {

    private Long id;
    private String type;
    private int additionalPrice;
    private String colorImage;
}
