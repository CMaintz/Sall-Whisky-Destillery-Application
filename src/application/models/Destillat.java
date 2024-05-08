package application.models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Destillat {
    private ArrayList<Påfyldning> påfyldning = new ArrayList<>();
    private String navn;
    private double antalLiter;
    private LocalDate påfyldningsDato;

    public Destillat(ArrayList<Påfyldning> påfyldning, String navn) {
        this.påfyldning = påfyldning;
        this.navn = navn;
        påfyldningsDato = LocalDate.now();
        for (Påfyldning pf : påfyldning) {
            antalLiter += pf.getLiterPåfyldt();
        }
    }

    public ArrayList<Påfyldning> getPåfyldning() {
        return påfyldning;
    }

    public String getNavn() {
        return navn;
    }

    public LocalDate getPåfyldningsDato() {
        return påfyldningsDato;
    }
}
