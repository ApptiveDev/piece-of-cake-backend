package apptive.com.common.base.impl;

import apptive.com.common.base.BaseController;
import apptive.com.common.base.BaseEntity;
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
public class BaseControllerImpl<E extends BaseEntity, Rs, R extends JpaRepository<E, Long>> implements BaseController<E, Rs, R> {

    private final BaseServiceImpl<E, Rs, R> baseService;

    @GetMapping
    public ResponseEntity<List<Rs>> findAll() {

        List<Rs> rsList = baseService.findAll();
        return new ResponseEntity<>(rsList, HttpStatus.OK);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {

        Long rs = baseService.delete(id);
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }
}
