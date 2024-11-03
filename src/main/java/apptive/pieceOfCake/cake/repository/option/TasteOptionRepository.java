package apptive.pieceOfCake.cake.repository.option;

import apptive.pieceOfCake.cake.model.option.TasteOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasteOptionRepository extends JpaRepository<TasteOption, Long> {
}
