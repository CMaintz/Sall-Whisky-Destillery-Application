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

    /**
     * Instantiates a new Whisky flaske.
     *
     * @param flaskeNr        flaske nr
     * @param whisky          whisky
     * @param produktHistorie produkt historie
     */
    public WhiskyFlaske(int flaskeNr, WhiskyProdukt whisky, String produktHistorie) {
        this.flaskeNr = flaskeNr;
        this.whisky = whisky;
        this.produktHistorie = produktHistorie;
    }

    /**
     * Gets produkt historie.
     *
     * @return produkt historie
     */
    public String getProduktHistorie() {
        return produktHistorie;
    }

    /**
     * Gets whisky.
     *
     * @return whisky
     */
    public WhiskyProdukt getWhisky() {
        return whisky;
    }


    @Override
    public String toString() {
        return whisky.getNavn() + " flaske #" + flaskeNr;
    }

}
