package apptive.com.store.store.model.value;

import apptive.com.store.store.model.request.StoreRegistrationRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter @Embeddable
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class BankInfo {

    private String businessRegistrationNumber; // 사업자등록번호
    private String accountHolder; // 예금주
    private String bankName; // 은행명
    private String accountNumber; // 계좌번호

    public void saveFrom(StoreRegistrationRequest dto) {
        this.businessRegistrationNumber = dto.getBusinessRegistrationNumber();
        this.accountHolder = dto.getAccountHolder();
        this.bankName = dto.getBankName();
        this.accountNumber = dto.getAccountNumber();
    }
}
