package apptive.com.store.cake.model;

import apptive.com.common.base.BaseEntity;
import apptive.com.store.cake.model.option.*;
import apptive.com.store.store.model.Store;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private String name; // 케이크명

    private String description; // 케이크 이름
    private String cakeImage; // 케이크 대표 이미지
    private int price; // 가격

    @OneToMany(mappedBy = "cake", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CakeOption> options = new ArrayList<>();
}
