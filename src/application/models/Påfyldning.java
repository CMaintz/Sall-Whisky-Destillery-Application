package application.models;

import java.time.LocalDate;


public class Påfyldning {
    private String medarbejderNavn;
    private double literPåfyldt;
    private Destillering destillering;

    //Pre: literPåFyldt skal være <= currentAntalLiter i valgte destillering
    Påfyldning(String medarbejderNavn, double literPåfyldt, Destillering destillering) {
        this.medarbejderNavn = medarbejderNavn;
        this.literPåfyldt = literPåfyldt;
        this.destillering = destillering;
        destillering.fjernAntalLiter(literPåfyldt);
        destillering.setSlutDato(LocalDate.now());
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



    @Override
    public String toString() {
        return medarbejderNavn + ", " + literPåfyldt + ", " + destillering;
    }
}
