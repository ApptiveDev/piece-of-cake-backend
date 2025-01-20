package apptive.pieceOfCake.order.model;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    CART, // 장바구니
    PURCHASE // 구매
}