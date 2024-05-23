package application.models;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The type Whisky flaske.
 */
public class WhiskyFlaske implements Serializable {
    private int flaskeNr;
    private String produktHistorie;
    private WhiskyProdukt whisky;
    private LocalDate flaskningsDato;

    /**
     * Instantiates a new Whisky flaske.
     *
     * @param flaskeNr        the flaske nr
     * @param whisky          the whisky
     * @param produktHistorie the produkt historie
     */
    public WhiskyFlaske(int flaskeNr, WhiskyProdukt whisky, String produktHistorie) {
        this.flaskeNr = flaskeNr;
        this.whisky = whisky;
        flaskningsDato = LocalDate.now();
        this.produktHistorie = produktHistorie;
    }

    /**
     * Gets flaske nr.
     *
     * @return the flaske nr
     */
    public int getFlaskeNr() {
        return flaskeNr;
    }

    /**
     * Gets produkt historie.
     *
     * @return the produkt historie
     */
    public String getProduktHistorie() {
        return produktHistorie;
    }

    /**
     * Gets whisky.
     *
     * @return the whisky
     */
    public WhiskyProdukt getWhisky() {
        return whisky;
    }

    /**
     * Gets flasknings dato.
     *
     * @return the flasknings dato
     */
    public LocalDate getFlaskningsDato() {
        return flaskningsDato;
    }

    @Override
    public String toString() {
        return "Flaske #" + flaskeNr;
    }

}
