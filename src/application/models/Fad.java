package application.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

/**
 * The type Fad.
 */
public class Fad implements Serializable {
    private static int antalFade = 0;
    private String fadNr;
    private int literKapacitet;
    private Destillat destillat;
    private FadHistorik fadHistorik;

    /**
     * Instantiates a new Fad.
     *
     * @param literKapacitet the liter kapacitet
     */
    public Fad(int literKapacitet) {
        this.literKapacitet = literKapacitet;
        antalFade++;
        this.fadNr = antalFade + "";
    }

    /**
     * Create fad historik fad historik.
     *
     * @param tidligereIndhold the tidligere indhold
     * @param land             the land
     * @param fraÅr            the fra år
     * @param leverandør       the leverandør
     * @return the fad historik
     */
    public FadHistorik createFadHistorik(String tidligereIndhold, String land, LocalDate fraÅr, String leverandør) {
        FadHistorik fh = new FadHistorik(tidligereIndhold, land, fraÅr, leverandør);
        this.fadHistorik = fh;
        return fh;
    }

    /**
     * Gets fad nr.
     *
     * @return the fad nr
     */
    public String getFadNr() {
        return fadNr;
    }

    /**
     * Gets liter kapacitet.
     *
     * @return the liter kapacitet
     */
    public int getLiterKapacitet() {
        return literKapacitet;
    }

    /**
     * Get alder måneder int.
     *
     * @return the int
     */
    public int getAlderMåneder() {
        return (int) fadHistorik.getFraÅr().until(LocalDate.now().plusDays(1), ChronoUnit.MONTHS);
    }

    /**
     * Gets antal fade.
     *
     * @return the antal fade
     */
    public static int getAntalFade() {
        return antalFade;
    }

    /**
     * Sets antal fade.
     *
     * @param antalFade the antal fade
     */
    public static void setAntalFade(int antalFade) {
        Fad.antalFade = antalFade;
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
     * Remove destillat.
     */
    public void removeDestillat() {
        this.destillat = null;
    }

    /**
     * Add destillat.
     *
     * @param destillat the destillat
     */
    public void addDestillat(Destillat destillat) {
        if (destillat != null) {
            this.fadHistorik.addDestillat(destillat);
            this.destillat = destillat;
            destillat.setFad(this);
        }
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return fadHistorik.getTidligereIndhold();
    }

    /**
     * Gets alder.
     *
     * @return the alder
     */
    public Period getAlder() {
        return this.fadHistorik.getFraÅr().until(LocalDate.now());
    }

    /**
     * Gets fad historik.
     *
     * @return the fad historik
     */
    public FadHistorik getFadHistorik() {
        return fadHistorik;
    }

    @Override
    public String toString() {
        return "#" + fadNr + " " + literKapacitet + "L " + getType();
    }

}
