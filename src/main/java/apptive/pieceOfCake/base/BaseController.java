package apptive.pieceOfCake.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface BaseController<E extends BaseEntity, Rs, R extends JpaRepository<E, Long>> {

    ResponseEntity<List<Rs>> findAll();
    ResponseEntity<Long> delete(@PathVariable("id") Long id);
}
