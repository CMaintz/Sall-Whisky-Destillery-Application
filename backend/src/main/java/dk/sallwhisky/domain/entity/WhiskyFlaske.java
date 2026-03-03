package dk.sallwhisky.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "whisky_flaske")
@Getter @Setter @NoArgsConstructor
public class WhiskyFlaske {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int flaskeNummer;

    @Column(columnDefinition = "TEXT")
    private String produktHistorie;

    private LocalDate flaskningsDato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "whisky_produkt_id", nullable = false)
    @JsonIgnore
    private WhiskyProdukt whiskyProdukt;

    public WhiskyFlaske(int flaskeNummer, String produktHistorie, WhiskyProdukt whiskyProdukt) {
        this.flaskeNummer = flaskeNummer;
        this.produktHistorie = produktHistorie;
        this.whiskyProdukt = whiskyProdukt;
        this.flaskningsDato = LocalDate.now();
    }
}
