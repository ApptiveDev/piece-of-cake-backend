package apptive.com.store.cake.repository.option;

import apptive.com.store.cake.model.option.TasteOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasteOptionRepository extends JpaRepository<TasteOption, Long> {
}
