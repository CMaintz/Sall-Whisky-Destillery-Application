package application.models;

import application.models.Destillering;

import java.time.LocalDate;
import java.util.ArrayList;


public class Påfyldning {
    private String medarbejderNavn;
    private int literPåfyldt;
    private Destillering destillering;

    public Påfyldning(String medarbejderNavn, int literPåfyldt, Destillering destillering) {
        this.medarbejderNavn = medarbejderNavn;
        this.literPåfyldt = literPåfyldt;
        this.destillering = destillering;
    }

    public String getMedarbejderNavn() {
        return medarbejderNavn;
    }

    public int getLiterPåfyldt() {
        return literPåfyldt;
    }

    public Destillering getDestillering() {
        return destillering;
    }
}
