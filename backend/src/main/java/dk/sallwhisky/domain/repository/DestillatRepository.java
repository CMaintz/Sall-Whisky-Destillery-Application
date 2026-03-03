package dk.sallwhisky.domain.repository;

import dk.sallwhisky.domain.entity.Destillat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DestillatRepository extends JpaRepository<Destillat, UUID> {
}
