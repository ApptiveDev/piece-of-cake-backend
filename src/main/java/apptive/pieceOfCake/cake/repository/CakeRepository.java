package apptive.pieceOfCake.cake.repository;

import apptive.pieceOfCake.cake.model.Cake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CakeRepository extends JpaRepository<Cake, Long> {
    List<Cake> findAllByStoreId(Long storeId);
}
