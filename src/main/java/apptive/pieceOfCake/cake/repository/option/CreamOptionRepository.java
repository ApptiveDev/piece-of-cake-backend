package apptive.pieceOfCake.cake.repository.option;

import apptive.pieceOfCake.cake.model.option.CreamOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreamOptionRepository extends JpaRepository<CreamOption, Long> {
}
