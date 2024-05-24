package application.models;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * Type Påfyldning.
 */
public class Påfyldning implements Serializable {
    private String medarbejderNavn;
    private double literPåfyldt;
    private Destillering destillering;

    /**
     * Instantiates a new Påfyldning.
     * pre: literPåfyldt <= currentAntalLiter i valgte destillering
     *
     * @param medarbejderNavn medarbejder navn
     * @param literPåfyldt    liter påfyldt
     * @param destillering    destillering
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
     * @return medarbejder navn
     */
    public String getMedarbejderNavn() {
        return medarbejderNavn;
    }

    /**
     * Get liter påfyldt double.
     *
     * @return double
     */
    public double getLiterPåfyldt() {
        return literPåfyldt;
    }

    /**
     * Gets destillering.
     *
     * @return destillering
     */
    public Destillering getDestillering() {
        return destillering;
    }

    /**
     * Gets detaljer.
     *
     * @return detaljer
     */
    public String getDetaljer() {
        return literPåfyldt + "L, " + "påfyldt af: " + medarbejderNavn + "\ndestillering: " + destillering.getDetaljer();
    }

    @Override
    public String toString() {
        return literPåfyldt + medarbejderNavn + destillering.toString();
    }
}
