package application.models;

import java.io.Serializable;

/**
 * The type Fad tapning.
 */
public class FadTapning implements Serializable {
    private String medarbejderNavn;
    private double literTappet;
    private Fad fad;
    private Destillat destillat;

    /**
     * Instantiates a new Fad tapning.
     *
     * @param medarbejderNavn the medarbejder navn
     * @param literTappet     the liter tappet
     * @param fad             the fad
     */
    FadTapning(String medarbejderNavn, double literTappet, Fad fad) {
        this.medarbejderNavn = medarbejderNavn;
        this.fad = fad;
        destillat = fad.getDestillat();
        tapning(literTappet);
    }

    private void tapning(double literTappet) {
        if (fad.getDestillat().getAntalLiter() >= literTappet) {
            this.literTappet = literTappet;
            fad.getDestillat().fjernAntalLiter(literTappet);
        }
    }

    /**
     * Gets destillat.
     *
     * @return the destillat
     */
    public Destillat getDestillat() {
        return destillat;
    }

    /**
     * Gets liter tappet.
     *
     * @return the liter tappet
     */
    public double getLiterTappet() {
        return literTappet;
    }

}
