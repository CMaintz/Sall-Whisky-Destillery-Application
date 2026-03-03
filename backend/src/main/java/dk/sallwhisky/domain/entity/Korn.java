package dk.sallwhisky.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "korn")
@Getter @Setter @NoArgsConstructor
public class Korn {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String sort;

    @NotBlank
    private String variant;

    @NotBlank
    private String markNavne;

    @OneToMany(mappedBy = "korn")
    @JsonIgnore
    private List<Destillering> destilleringer = new ArrayList<>();

    public Korn(String sort, String variant, String markNavne) {
        this.sort = sort;
        this.variant = variant;
        this.markNavne = markNavne;
    }

    @Override
    public String toString() {
        return variant + " " + sort;
    }
}
