package dk.sallwhisky.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "paafyldning")
@Getter @Setter @NoArgsConstructor
public class Paafyldning {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String medarbejder;

    @Positive
    private double literPaafyldt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destillering_id", nullable = false)
    private Destillering destillering;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destillat_id", nullable = false)
    @JsonIgnore
    private Destillat destillat;

    public Paafyldning(String medarbejder, double literPaafyldt, Destillering destillering) {
        this.medarbejder = medarbejder;
        this.literPaafyldt = literPaafyldt;
        this.destillering = destillering;
    }
}
