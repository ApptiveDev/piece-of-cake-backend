package apptive.com.store.store.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StoreUpdateRequest {

    private String etcStoreInfo; // 기타 가게 정보
    private String snsLink; // SNS 링크

    public void initialize() {
        if(etcStoreInfo == null) etcStoreInfo = "";
        if(snsLink == null) snsLink = "";
    }
}
