package application.models;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Påfyldning implements Serializable {
    private String medarbejderNavn;
    private double literPåfyldt;
    private Destillering destillering;

    //Pre: literPåFyldt skal være <= currentAntalLiter i valgte destillering
    Påfyldning(String medarbejderNavn, double literPåfyldt, Destillering destillering) {
        this.medarbejderNavn = medarbejderNavn;
        this.literPåfyldt = literPåfyldt;
        this.destillering = destillering;
        destillering.fjernAntalLiter(literPåfyldt);
        if (destillering.getSlutDato() == null) {
            destillering.setSlutDato(LocalDateTime.now());
        }
    }

    public String getMedarbejderNavn() {
        return medarbejderNavn;
    }

    public double getLiterPåfyldt() {
        return literPåfyldt;
    }

    public Destillering getDestillering() {
        return destillering;
    }

    public String getDetaljer() {
        return literPåfyldt + "L, " + "påfyldt af: " + medarbejderNavn + "\ndestillering: " + destillering.getDetaljer();
    }

    @Override
    public String toString() {
        return literPåfyldt + medarbejderNavn + destillering.toString();
    }
}
