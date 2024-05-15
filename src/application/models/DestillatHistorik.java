package application.models;

import java.time.LocalDate;

public class DestillatHistorik {
    private Fad fad;
    private LocalDate startDato;
    private LocalDate slutDato;
    private Destillat destillat;

    public DestillatHistorik(Fad fad, LocalDate startDato, LocalDate slutDato, Destillat destillat) {
        this.fad = fad;
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.destillat = destillat;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

}
