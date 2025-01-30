package apptive.com.member.order.model.payment;

import apptive.com.member.order.model.request.OrderRequest;
import apptive.com.store.store.model.request.StoreRegistrationRequest;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Embeddable
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PaymentInfo {

    private String cardNum; // 카드 번호
    private String expirationDate; // 유효기간
    private String password; // 비밀번호
    private String cvcCode; // 보안코드

    public void saveFrom(OrderRequest dto) {
        this.cardNum = dto.getCardNum();
        this.expirationDate = dto.getExpirationDate();
        this.password = dto.getPassword();
        this.cvcCode = dto.getCvcCode();
    }
}
