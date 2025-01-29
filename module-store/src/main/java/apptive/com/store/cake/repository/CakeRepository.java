package apptive.com.store.cake.repository;

import apptive.com.store.cake.model.Cake;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CakeRepository extends JpaRepository<Cake, Long> {
    List<Cake> findAllByStoreId(Long storeId);
    Page<Cake> findAllByStoreIdOrderByName(Long storeId, Pageable pageable);
}
