package apptive.pieceOfCake.cake.repository.option;

import apptive.pieceOfCake.cake.model.option.SizeOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeOptionRepository extends JpaRepository<SizeOption, Long> {
}
