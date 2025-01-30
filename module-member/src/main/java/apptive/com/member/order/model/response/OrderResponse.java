package apptive.com.member.order.model.response;

import apptive.com.member.order.model.OrderInfo;
import apptive.com.member.order.model.dto.OrderOptionDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record OrderResponse (Long orderId,
                            String memo,
                            List<OrderOptionDto> optionDtos) {

    public static OrderResponse of(OrderInfo orderInfo) {

        List<OrderOptionDto> optionDtos = orderInfo.getOptions().stream()
                .map(option -> new OrderOptionDto(option.getType(), option.getValue(), option.getPrice()))
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .orderId(orderInfo.getId())
                .memo(orderInfo.getMemo())
                .optionDtos(optionDtos)
                .build();
    }
}
