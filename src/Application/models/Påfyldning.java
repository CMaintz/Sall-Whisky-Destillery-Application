package Application.models;

import java.time.LocalDate;

public class Påfyldning {
    private String medarbejderNavn;
    private int literPåfyldt;
    private Destillering destillat;
    private LocalDate påfyldningsDato;

    public Påfyldning(String medarbejderNavn, int literPåfyldt, Destillering destillat) {
        this.medarbejderNavn = medarbejderNavn;
        this.literPåfyldt = literPåfyldt;
        this.destillat = destillat;
        påfyldningsDato = LocalDate.now();
    }

    public String getMedarbejderNavn() {
        return medarbejderNavn;
    }

    public int getLiterPåfyldt() {
        return literPåfyldt;
    }

    public Destillering getDestillat() {
        return destillat;
    }

    public LocalDate getPåfyldningsDato() {
        return påfyldningsDato;
    }
}
