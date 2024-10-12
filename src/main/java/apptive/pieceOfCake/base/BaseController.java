package apptive.pieceOfCake.base;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
@ResponseBody
@RequiredArgsConstructor
public class BaseController<E extends BaseEntity, Rq, Rs, R extends JpaRepository<E, Long>> {

    private final BaseService<E, Rs, R> baseService;

    @GetMapping
    public ResponseEntity<Object> findAll() {

        List<Rs> rsList = baseService.findAll();
        return new ResponseEntity<>(rsList, HttpStatus.OK);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {

        Long rs = baseService.delete(id);
        return null;
    }
}
