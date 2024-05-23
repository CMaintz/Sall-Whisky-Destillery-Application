package application.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Fad historik.
 */
public class FadHistorik implements Serializable {
    private String tidligereIndhold;
    private String land;
    private LocalDate fraÅr;
    private LocalDate tilÅr;
    private String leverandør;
    private List<Destillat> tidligereDestillater;

    /**
     * Instantiates a new Fad historik.
     *
     * @param tidligereIndhold the tidligere indhold
     * @param land             the land
     * @param fraÅr            the fra år
     * @param leverandør       the leverandør
     */
    FadHistorik(String tidligereIndhold, String land, LocalDate fraÅr, String leverandør) {
        this.tidligereIndhold = tidligereIndhold;
        this.land = land;
        this.fraÅr = fraÅr;
        this.tilÅr = LocalDate.now();
        this.leverandør = leverandør;
        this.tidligereDestillater = new ArrayList<>();
    }

    /**
     * Gets tidligere indhold.
     *
     * @return the tidligere indhold
     */
    public String getTidligereIndhold() {
        return tidligereIndhold;
    }

    /**
     * Sets tidligere indhold.
     *
     * @param tidligereIndhold the tidligere indhold
     */
    public void setTidligereIndhold(String tidligereIndhold) {
        this.tidligereIndhold = tidligereIndhold;
    }

    /**
     * Gets land.
     *
     * @return the land
     */
    public String getLand() {
        return land;
    }

    /**
     * Sets land.
     *
     * @param land the land
     */
    public void setLand(String land) {
        this.land = land;
    }

    /**
     * Get fra år local date.
     *
     * @return the local date
     */
    public LocalDate getFraÅr() {
        return fraÅr;
    }

    /**
     * Get til år local date.
     *
     * @return the local date
     */
    public LocalDate getTilÅr() {
        return tilÅr;
    }

    /**
     * Get leverandør string.
     *
     * @return the string
     */
    public String getLeverandør() {
        return leverandør;
    }

    /**
     * Set leverandør.
     *
     * @param leverandør the leverandør
     */
    public void setLeverandør(String leverandør) {
        this.leverandør = leverandør;
    }

    /**
     * Gets tidligere destillater.
     *
     * @return the tidligere destillater
     */
    public List<Destillat> getTidligereDestillater() {
        return tidligereDestillater;
    }

    /**
     * Add destillat.
     *
     * @param destillat the destillat
     */
    public void addDestillat(Destillat destillat) {
        if (!tidligereDestillater.contains(destillat)) {
            tidligereDestillater.add(destillat);
        }
    }


    @Override
    public String toString() {
        return "fadtype: " + tidligereIndhold + "\nland: " + land
                + "\nfra " + fraÅr + "\ntil " + tilÅr + "\nleverandør: " + leverandør;
    }
}
