package apptive.pieceOfCake.cake.model;

import apptive.pieceOfCake.base.BaseEntity;
import apptive.pieceOfCake.cake.model.option.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@SQLRestriction("is_deleted = 0")
public class Cake extends BaseEntity {

    @Column(nullable = false)
    private Long storeId;

    private String name; // 케이크명
    private String description; // 케이크 이름
    private String cakeImage; // 케이크 대표 이미지
    private int price; // 가격

    @OneToMany(mappedBy = "cake", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SizeOption> sizeOptions = new ArrayList<>(); // 사이즈 옵션
    @OneToMany(mappedBy = "cake", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TasteOption> tasteOptions = new ArrayList<>(); // 맛 옵션
    @OneToMany(mappedBy = "cake", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreamOption> creamOptions = new ArrayList<>(); // 크림 옵션
    @OneToMany(mappedBy = "cake", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ColorOption> colorOptions = new ArrayList<>(); // 색상 옵션
    @OneToMany(mappedBy = "cake", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EtcOption> etcOptions = new ArrayList<>(); // 기타 옵션
}
