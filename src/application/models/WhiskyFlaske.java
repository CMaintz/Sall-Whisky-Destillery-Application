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

    public String genererHistorie() {
        //TODO - Er det i stedet for en beskrivelse?
        // Eller skal den gemme det den genererer, som beskrivelsen?
        // Og skal historien kunne gemmes til en fil?

        String toReturn = "";


        toReturn += "\n" + whiskyProdukt.whiskyType();
        return toReturn;
    }

}
