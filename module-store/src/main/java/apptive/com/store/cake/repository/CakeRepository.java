package apptive.com.store.cake.repository;

import apptive.com.store.cake.model.Cake;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CakeRepository extends JpaRepository<Cake, Long> {
    Page<Cake> findAllByStoreId(Long storeId, Pageable pageable);
    Page<Cake> findAllByStoreIdOrderByName(Long storeId, Pageable pageable);
}
