package dk.sallwhisky.domain.repository;

import dk.sallwhisky.domain.entity.Fad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FadRepository extends JpaRepository<Fad, UUID> {

    @Query("SELECT f FROM Fad f WHERE f.destillat IS NULL")
    List<Fad> findTommeFade();

    @Query("SELECT f FROM Fad f WHERE f.destillat IS NOT NULL")
    List<Fad> findFyldteFade();

    // Barrels with a destillat that has been maturing for 3+ years
    @Query("SELECT f FROM Fad f WHERE f.destillat IS NOT NULL AND f.destillat.startDato <= :cutoff")
    List<Fad> findFadeKlar(@org.springframework.data.repository.query.Param("cutoff") java.time.LocalDate cutoff);
}
