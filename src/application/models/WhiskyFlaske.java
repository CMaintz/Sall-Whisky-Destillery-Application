package application.models;

import java.io.Serializable;
import java.time.LocalDate;

public class WhiskyFlaske implements Serializable {
    private int flaskeNr;
    private String produktHistorie;
    private WhiskyProdukt whisky;
    private LocalDate flaskningsDato;

    public WhiskyFlaske(int flaskeNr, WhiskyProdukt whisky, String produktHistorie) {
        this.flaskeNr = flaskeNr;
        this.whisky = whisky;
        flaskningsDato = LocalDate.now();
        this.produktHistorie = "Flaske #" + flaskeNr + " " + produktHistorie;
    }

    public int getFlaskeNr() {
        return flaskeNr;
    }

    public String getProduktHistorie() {
        return produktHistorie;
    }

    public WhiskyProdukt getWhisky() {
        return whisky;
    }

    public LocalDate getFlaskningsDato() {
        return flaskningsDato;
    }

    @Override
    public String toString() {
        return "Flaske #" + flaskeNr;
    }

}
