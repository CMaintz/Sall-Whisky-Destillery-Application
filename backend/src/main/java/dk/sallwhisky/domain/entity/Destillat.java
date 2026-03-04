package dk.sallwhisky.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "destillat")
@Getter @Setter @NoArgsConstructor
public class Destillat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private double antalLiter;
    private double alkoholProcent;
    private LocalDate startDato;

    @OneToMany(mappedBy = "destillat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paafyldning> paafyldninger = new ArrayList<>();

    @OneToMany(mappedBy = "destillat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModningsHistorik> modningsHistorik = new ArrayList<>();

    @OneToOne(mappedBy = "destillat")
    @JsonIgnore
    private Fad fad;

    /**
     * A destillat is ready for bottling after 3 years of maturation.
     * This is the core business rule - the legal minimum for whisky.
     */
    public boolean erKlar() {
        if (startDato == null) return false;
        return Period.between(startDato, LocalDate.now()).getYears() >= 3;
    }

    public void tilfoejPaafyldning(Paafyldning pf) {
        paafyldninger.add(pf);
        pf.setDestillat(this);
        antalLiter += pf.getLiterPaafyldt();
        udregnAlkoholProcent();
    }

    private void udregnAlkoholProcent() {
        double literEthanol = paafyldninger.stream()
                .mapToDouble(p -> (p.getDestillering().getAlkoholProcent() / 100.0) * p.getLiterPaafyldt())
                .sum();
        alkoholProcent = antalLiter > 0 ? (literEthanol / antalLiter) * 100.0 : 0;
    }
}
