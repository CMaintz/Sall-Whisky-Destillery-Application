package application.models;

import java.time.LocalDate;

public class Omhældning {
    private Fad fad;
    private LocalDate startDato;
    private LocalDate slutDato;

    Omhældning(Fad fad, LocalDate startDato, LocalDate slutDato) {
        this.fad = fad;
        this.startDato = startDato;
        this.slutDato = slutDato;
    }

    public Fad getFad() {
        return fad;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }
}
