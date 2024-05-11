package application.models;

import application.models.Destillering;

import java.time.LocalDate;
import java.util.ArrayList;


public class Påfyldning {
    private String medarbejderNavn;
    private double literPåfyldt;
    private Destillering destillering;

    //Pre: literPåFyldt skal være <= currentAntalLiter i destilat
    public Påfyldning(String medarbejderNavn, double literPåfyldt, Destillering destillering) {
        this.medarbejderNavn = medarbejderNavn;
        this.literPåfyldt = literPåfyldt;
        this.destillering = destillering;
        setSamletAntalLiterDestillering(literPåfyldt);
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

    private void setSamletAntalLiterDestillering(double literPåfyldt) {
        destillering.setCurrentAntalLiter(literPåfyldt);
    }

    @Override
    public String toString() {
        return medarbejderNavn + ", " + literPåfyldt + ", " + destillering;
    }
}
