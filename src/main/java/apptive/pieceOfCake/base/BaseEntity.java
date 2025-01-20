package apptive.pieceOfCake.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isDeleted = Boolean.FALSE;

    @Column(name="CREATE_AT", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createAt;

    @Column(name="UPDATE_AT", nullable = false)
    @LastModifiedDate
    private LocalDateTime updateAt;
}
