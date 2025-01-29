package apptive.com.store.cake.model.option;

import apptive.com.store.cake.model.Cake;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TasteOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cake_id") // 외래 키 설정
    private Cake cake;

    private String type; // 설명
    private int additionalPrice; // 추가 가격
}
