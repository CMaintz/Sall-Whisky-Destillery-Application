package dk.sallwhisky.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "whisky_produkt")
@Getter @Setter @NoArgsConstructor
public class WhiskyProdukt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String navn;

    private double alkoholProcent;
    private double antalLiter;
    private double literVandTilfojet;

    @OneToMany(mappedBy = "whiskyProdukt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FadTapning> fadTapninger = new ArrayList<>();

    @OneToMany(mappedBy = "whiskyProdukt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WhiskyFlaske> flasker = new ArrayList<>();

    public WhiskyProdukt(String navn) {
        this.navn = navn;
    }

    /**
     * Classifies whisky type based on composition:
     * - Cask Strength: single barrel, no water added, no re-barrelling
     * - Single Cask:   single barrel, water added
     * - Single Malt:   multiple barrels or re-barrelled
     */
    public String whiskyType() {
        if (fadTapninger.size() == 1) {
            Destillat destillat = fadTapninger.get(0).getDestillat();
            if (destillat != null && destillat.getModningsHistorik().size() == 1) {
                return literVandTilfojet == 0 ? "Cask Strength" : "Single Cask";
            }
        }
        return "Single Malt";
    }

    public void udregnAlkoholProcent() {
        double literEthanol = fadTapninger.stream()
                .mapToDouble(ft -> {
                    if (ft.getDestillat() == null) return 0;
                    return (ft.getDestillat().getAlkoholProcent() / 100.0) * ft.getLiterTappet();
                })
                .sum();
        alkoholProcent = antalLiter > 0 ? (literEthanol / antalLiter) * 100.0 : 0;
    }

    public void tilfoejVand(double liter) {
        literVandTilfojet += liter;
        antalLiter += liter;
        udregnAlkoholProcent();
    }
}
