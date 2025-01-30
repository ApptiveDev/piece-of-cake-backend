package apptive.com.store.cake.model.option;

import apptive.com.common.base.BaseEntity;
import apptive.com.store.cake.model.Cake;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CakeOption extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cake_id", nullable = false)
    private Cake cake;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OptionType type; // SIZE, TASTE, CREAM, COLOR, ETC

    @Column(nullable = false)
    private String value; // 옵션 이름

    @Column(nullable = false)
    private int price; // 옵션 가격
}
