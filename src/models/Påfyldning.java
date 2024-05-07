package models;

import java.time.LocalDate;

public class Påfyldning {
    private String medarbejderNavn;
    private int literPåfyldt;
    private LocalDate startDato;
    private Destillering destillering;

    public Påfyldning(String medarbejderNavn, int literPåfyldt, LocalDate startDato) {
        this.medarbejderNavn = medarbejderNavn;
        this.literPåfyldt = literPåfyldt;
        this.startDato = startDato;
    }

    public LocalDate getStartDato() {
        return startDato;
    }
}
