package application.models;

import java.time.LocalDate;

public class WhiskyFlaske {
    private int flaskeNr;
    private WhiskyProdukt whiskyProdukt;
    private LocalDate flaskningsDato;

    public WhiskyFlaske(int flaskeNr, WhiskyProdukt whiskyProdukt) {
        this.flaskeNr = flaskeNr;
        this.whiskyProdukt = whiskyProdukt;
        flaskningsDato = LocalDate.now();
    }

}
