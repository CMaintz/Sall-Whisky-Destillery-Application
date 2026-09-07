package dk.sallwhisky.domain.repository;

import dk.sallwhisky.domain.entity.Hylde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface HyldeRepository extends JpaRepository<Hylde, UUID> {

    @Query("SELECT h FROM Hylde h WHERE h.fad IS NULL")
    List<Hylde> findLedigeHylder();
}
