package application.models;

import java.io.Serializable;
import java.time.LocalDate;

public class ModningsHistorik implements Serializable {
    private Fad fad;
    private LocalDate startDato;
    private LocalDate slutDato;

    ModningsHistorik(Fad fad, LocalDate startDato) {
        this.fad = fad;
        this.startDato = startDato;
        this.slutDato = null;
    }

    public void setStartDato(LocalDate startDato) {
        this.startDato = startDato;
    }

    public Fad getFad() {
        return fad;
    }

    public void setSlutDato(LocalDate slutDato) {
        this.slutDato = slutDato;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }
}
