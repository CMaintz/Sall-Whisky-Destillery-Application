package dk.sallwhisky.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "destillering")
@Getter @Setter @NoArgsConstructor
public class Destillering {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private int batchNummer;

    @NotBlank
    private String maltBatch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "korn_id", nullable = false)
    private Korn korn;

    @NotBlank
    private String medarbejder;

    @Positive
    private double antalLiter;

    @Positive
    private double alkoholProcent;

    private String rygemateriale;
    private String kommentar;

    private LocalDateTime startTidspunkt;
    private LocalDateTime slutTidspunkt;

    @OneToMany(mappedBy = "destillering")
    @JsonIgnore
    private List<Paafyldning> paafyldninger = new ArrayList<>();

    public Destillering(int batchNummer, String maltBatch, Korn korn, String medarbejder,
                        double antalLiter, double alkoholProcent, String rygemateriale, String kommentar) {
        this.batchNummer = batchNummer;
        this.maltBatch = maltBatch;
        this.korn = korn;
        this.medarbejder = medarbejder;
        this.antalLiter = antalLiter;
        this.alkoholProcent = alkoholProcent;
        this.rygemateriale = rygemateriale;
        this.kommentar = kommentar;
        this.startTidspunkt = LocalDateTime.now();
    }

    public long getDestilleringsTid() {
        if (startTidspunkt == null || slutTidspunkt == null) return 0;
        return startTidspunkt.until(slutTidspunkt, ChronoUnit.HOURS) + 1;
    }
}
