package dk.sallwhisky.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "reol")
@Getter @Setter @NoArgsConstructor
public class Reol {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int reolNummer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lager_id", nullable = false)
    @JsonIgnore
    private Lager lager;

    @OneToMany(mappedBy = "reol", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hylde> hylder = new ArrayList<>();

    public Reol(int reolNummer, Lager lager) {
        this.reolNummer = reolNummer;
        this.lager = lager;
    }

    public Hylde addHylde() {
        int nextPosition = hylder.size() + 1;
        Hylde hylde = new Hylde(nextPosition, this);
        hylder.add(hylde);
        return hylde;
    }
}
