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
@Table(name = "lager")
@Getter @Setter @NoArgsConstructor
public class Lager {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String navn;

    @OneToMany(mappedBy = "lager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reol> reoler = new ArrayList<>();

    public Lager(String navn) {
        this.navn = navn;
    }

    public Reol addReol() {
        int nextNummer = reoler.size() + 1;
        Reol reol = new Reol(nextNummer, this);
        reoler.add(reol);
        return reol;
    }
}
