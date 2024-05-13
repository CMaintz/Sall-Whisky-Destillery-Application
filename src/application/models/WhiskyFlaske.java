package application.models;

import java.time.LocalDate;

public class WhiskyFlaske {
    private int nummer;
    private WhiskyProdukt whiskyProdukt;
    private LocalDate flaskningsDato;

    public WhiskyFlaske(int nummer, WhiskyProdukt whiskyProdukt) {
        this.nummer = nummer;
        this.whiskyProdukt = whiskyProdukt;
        flaskningsDato = LocalDate.now();
    }
}
