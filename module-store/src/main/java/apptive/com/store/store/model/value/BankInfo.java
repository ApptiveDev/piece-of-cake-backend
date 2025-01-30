package apptive.com.store.store.model.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter @Embeddable
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankInfo {

    private String businessRegistrationNumber; // 사업자등록번호
    private String accountHolder; // 예금주
    private String bankName; // 은행명
    private String accountNumber; // 계좌번호
}
