package dk.sallwhisky.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "fad")
@Getter @Setter @NoArgsConstructor
public class Fad {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String fadNummer;

    @Positive
    private int literKapacitet;

    // Previous contents — Sherry, Bourbon, red wine, etc.
    @NotBlank
    private String tidligereIndhold;

    @NotBlank
    private String land;

    private LocalDate fraAar;

    @NotBlank
    private String leverandoer;

    // The barrel currently holds this destillat (null = empty barrel)
    @OneToOne
    @JoinColumn(name = "destillat_id")
    private Destillat destillat;

    // Back-reference: Hylde owns this relationship (FK is in hylde table)
    @OneToOne(mappedBy = "fad")
    @JsonIgnore
    private Hylde hylde;

    public boolean harDestillat() {
        return destillat != null;
    }

    public boolean erKlar() {
        return destillat != null && destillat.erKlar();
    }

    public int getAlderMaaneder() {
        if (fraAar == null) return 0;
        return (int) fraAar.until(LocalDate.now().plusDays(1), ChronoUnit.MONTHS);
    }

    public int getAlderAar() {
        if (fraAar == null) return 0;
        return (int) ChronoUnit.YEARS.between(fraAar, LocalDate.now());
    }
}
