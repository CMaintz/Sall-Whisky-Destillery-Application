package dk.sallwhisky.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "modnings_historik")
@Getter @Setter @NoArgsConstructor
public class ModningsHistorik {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destillat_id", nullable = false)
    @JsonIgnore
    private Destillat destillat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fad_id", nullable = false)
    private Fad fad;

    private LocalDate paafyldningsDato;
    private LocalDate slutDato;

    public ModningsHistorik(Destillat destillat, Fad fad, LocalDate paafyldningsDato) {
        this.destillat = destillat;
        this.fad = fad;
        this.paafyldningsDato = paafyldningsDato;
    }
}
