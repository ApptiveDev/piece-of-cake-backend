package apptive.pieceOfCake.order.repository;

import apptive.pieceOfCake.order.model.dto.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
}
