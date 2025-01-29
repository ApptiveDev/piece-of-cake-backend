package apptive.com.store.cake.repository.option;

import apptive.com.store.cake.model.option.EtcOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtcOptionRepository extends JpaRepository<EtcOption, Long> {
}
