package dk.sallwhisky.domain.repository;

import dk.sallwhisky.domain.entity.WhiskyProdukt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WhiskyProduktRepository extends JpaRepository<WhiskyProdukt, UUID> {
}
