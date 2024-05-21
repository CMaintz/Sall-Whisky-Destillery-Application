package application.models;

import java.time.LocalDate;

public class ModningsHistorik {
    private Fad fad;
    private LocalDate startDato;
    private LocalDate slutDato;

    ModningsHistorik(Fad fad, LocalDate startDato) {
        this.fad = fad;
        this.startDato = startDato;
        this.slutDato = null;
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
