package apptive.pieceOfCake.store.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreFindByUserLonLat {

    @NotBlank(message = "위도를 확인해주세요.")
    @Min(value = -90, message = "위도의 범위는 -90~+90 입니다.")
    @Max(value = 90, message = "위도의 범위는 -90~+90 입니다.")
    double latitude;
    @NotBlank(message = "경도를 확인해주세요.")
    @Min(value = -180, message = "경도의 범위는 -180~+180 입니다.")
    @Max(value = 180, message = "경도의 범위는 -180~+180 입니다.")
    double longitude;
}
