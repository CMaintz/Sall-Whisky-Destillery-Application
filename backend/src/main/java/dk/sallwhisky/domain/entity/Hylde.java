package dk.sallwhisky.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "hylde")
@Getter @Setter @NoArgsConstructor
public class Hylde {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reol_id", nullable = false)
    @JsonIgnore
    private Reol reol;

    // The shelf holds one barrel at a time (nullable = empty shelf)
    @OneToOne
    @JoinColumn(name = "fad_id")
    private Fad fad;

    public Hylde(int position, Reol reol) {
        this.position = position;
        this.reol = reol;
    }

    public boolean erLedig() {
        return fad == null;
    }
}
