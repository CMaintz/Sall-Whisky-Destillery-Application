package dk.sallwhisky.domain.repository;

import dk.sallwhisky.domain.entity.Korn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface KornRepository extends JpaRepository<Korn, UUID> {
}
