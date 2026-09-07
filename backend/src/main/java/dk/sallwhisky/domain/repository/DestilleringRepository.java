package dk.sallwhisky.domain.repository;

import dk.sallwhisky.domain.entity.Destillering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DestilleringRepository extends JpaRepository<Destillering, UUID> {

    int countBy();
}
