package apptive.com.store.cake.model.response;

import apptive.com.store.cake.model.Cake;
import apptive.com.store.cake.model.option.dto.CakeOptionDto;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record CakeDetailResponse(Long cakeId,
                                 String name,
                                 String description,
                                 String cakeImage,
                                 int price,
                                 List<CakeOptionDto> optionDtos
                                 ) {

    public static CakeDetailResponse of(Cake cake) {

        List<CakeOptionDto> optionDtos = cake.getOptions().stream()
                .map(option -> new CakeOptionDto(option.getType(), option.getValue(), option.getPrice()))
                .collect(Collectors.toList());

        return CakeDetailResponse.builder()
                .cakeId(cake.getId())
                .name(cake.getName())
                .description(cake.getDescription())
                .cakeImage(cake.getCakeImage())
                .price(cake.getPrice())
                .optionDtos(optionDtos)
                .build();
    }
}
