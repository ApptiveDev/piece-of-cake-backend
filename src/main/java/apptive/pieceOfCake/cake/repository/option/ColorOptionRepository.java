package apptive.pieceOfCake.cake.repository.option;

import apptive.pieceOfCake.cake.model.option.ColorOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorOptionRepository extends JpaRepository<ColorOption, Long> {
}
