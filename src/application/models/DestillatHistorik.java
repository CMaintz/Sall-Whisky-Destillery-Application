package application.models;

import java.time.LocalDate;

public class DestillatHistorik {
    private Fad fad;
    private LocalDate startDato;
    private LocalDate slutDato;

    public DestillatHistorik(Fad fad, LocalDate startDato, LocalDate slutDato) {
        this.fad = fad;
        this.startDato = startDato;
        this.slutDato = slutDato;
    }

}
