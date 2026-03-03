package dk.sallwhisky.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "fad_tapning")
@Getter @Setter @NoArgsConstructor
public class FadTapning {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String medarbejder;

    private double literTappet;
    private LocalDate tapningsDato;

    // We keep a reference to the Fad for provenance tracking even after the barrel is emptied
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fad_id")
    private Fad fad;

    // Snapshot of the destillat at tap time — needed for ABV calculation and product history
    // even after fad.destillat has been set to null
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destillat_id")
    private Destillat destillat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "whisky_produkt_id", nullable = false)
    @JsonIgnore
    private WhiskyProdukt whiskyProdukt;

    public FadTapning(String medarbejder, double literTappet, Fad fad, Destillat destillat, WhiskyProdukt whiskyProdukt) {
        this.medarbejder = medarbejder;
        this.literTappet = literTappet;
        this.fad = fad;
        this.destillat = destillat;
        this.whiskyProdukt = whiskyProdukt;
        this.tapningsDato = LocalDate.now();
    }
}
