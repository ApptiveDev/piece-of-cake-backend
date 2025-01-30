package apptive.com.store.cake.model.request;

import apptive.com.store.cake.model.option.dto.CakeOptionDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CakeRequest {
    private String name;
    private String description;
    private int price;
    private List<CakeOptionDto> options;
}
