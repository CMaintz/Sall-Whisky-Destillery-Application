package application.models;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * The type Påfyldning.
 */
public class Påfyldning implements Serializable {
    private String medarbejderNavn;
    private double literPåfyldt;
    private Destillering destillering;

    /**
     * Instantiates a new Påfyldning.
     * pre: literPåfyldt <= currentAntalLiter i valgte destillering
     *
     * @param medarbejderNavn the medarbejder navn
     * @param literPåfyldt    the liter påfyldt
     * @param destillering    the destillering
     */
    Påfyldning(String medarbejderNavn, double literPåfyldt, Destillering destillering) {
        this.medarbejderNavn = medarbejderNavn;
        this.literPåfyldt = literPåfyldt;
        this.destillering = destillering;
        destillering.fjernAntalLiter(literPåfyldt);
        if (destillering.getSlutTidspunkt() == null) {
            destillering.setSlutTidspunkt(LocalDateTime.now());
        }
    }

    /**
     * Gets medarbejder navn.
     *
     * @return the medarbejder navn
     */
    public String getMedarbejderNavn() {
        return medarbejderNavn;
    }

    /**
     * Get liter påfyldt double.
     *
     * @return the double
     */
    public double getLiterPåfyldt() {
        return literPåfyldt;
    }

    /**
     * Gets destillering.
     *
     * @return the destillering
     */
    public Destillering getDestillering() {
        return destillering;
    }

    /**
     * Gets detaljer.
     *
     * @return the detaljer
     */
    public String getDetaljer() {
        return literPåfyldt + "L, " + "påfyldt af: " + medarbejderNavn + "\ndestillering: " + destillering.getDetaljer();
    }

    @Override
    public String toString() {
        return literPåfyldt + medarbejderNavn + destillering.toString();
    }
}
