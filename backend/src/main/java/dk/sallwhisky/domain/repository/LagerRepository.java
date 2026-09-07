package dk.sallwhisky.domain.repository;

import dk.sallwhisky.domain.entity.Lager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LagerRepository extends JpaRepository<Lager, UUID> {
}
