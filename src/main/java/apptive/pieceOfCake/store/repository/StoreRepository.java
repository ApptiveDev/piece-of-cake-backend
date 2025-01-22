package apptive.pieceOfCake.store.repository;

import apptive.pieceOfCake.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query(value = "SELECT * FROM store " +
            "WHERE (6371 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * " +
            "cos(radians(longitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(latitude)))) < :radius",
            nativeQuery = true)
    List<Store> findStoresWithinRadius(@Param("latitude") double latitude,
                                       @Param("longitude") double longitude,
                                       @Param("radius") double radius);

    Optional<Store> findByLoginId(String loginId);
}
