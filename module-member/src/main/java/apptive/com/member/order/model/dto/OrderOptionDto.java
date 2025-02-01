package apptive.com.member.order.model.dto;

import apptive.com.common.cake.OptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderOptionDto {
    private OptionType type;
    private String value;
    private int price;
}
