package apptive.com.store.cake.repository.option;

import apptive.com.store.cake.model.option.ColorOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorOptionRepository extends JpaRepository<ColorOption, Long> {
}
