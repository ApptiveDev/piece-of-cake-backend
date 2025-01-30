package apptive.com.store.cake.model.option.dto;

import apptive.com.store.cake.model.option.OptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CakeOptionDto {
    private OptionType type;
    private String value;
    private int price;
}

