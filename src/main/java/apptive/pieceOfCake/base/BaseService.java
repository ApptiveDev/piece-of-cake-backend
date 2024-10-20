package apptive.pieceOfCake.base;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseService<E extends BaseEntity, Rs, R extends JpaRepository<E, Long>> {

    List<Rs> findAll();
    Long delete(Long id);
}
