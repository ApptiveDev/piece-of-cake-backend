package apptive.pieceOfCake.cake.model.response;

import apptive.pieceOfCake.cake.model.response.option.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CakeResponse {

    private Long cakeId;
    private Long storeId;
    private String cakeName;
    private String description;
    private int price;
    private String storeName;
    private String cakeImage;

    private List<SizeOptionResponse> sizeOption;
    private List<TasteOptionResponse> tasteOption;
    private List<CreamOptionResponse> creamOption;
    private List<ColorOptionResponse> colorOption;
    private List<EtcOptionResponse> etcOption;
}
