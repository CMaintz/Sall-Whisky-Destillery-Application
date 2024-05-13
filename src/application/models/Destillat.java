package application.models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Destillat {
    private ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
    private String navn;
    private double antalLiter;
    private LocalDate påfyldningsDato;
    private double alkoholprocent;

    public Destillat(ArrayList<Påfyldning> påfyldning, String navn) {
        this.påfyldninger = påfyldning;
        this.navn = navn;
        påfyldningsDato = LocalDate.now();
        for (Påfyldning pf : påfyldning) {
            antalLiter += pf.getLiterPåfyldt();
        }
        setAlkoholprocent();
    }

    public ArrayList<Påfyldning> getPåfyldninger() {
        return påfyldninger;
    }

    public String getNavn() {
        return navn;
    }

    public LocalDate getPåfyldningsDato() {
        return påfyldningsDato;
    }

    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    public double getAntalLiter() {
        return antalLiter;
    }

    public void setAntalLiter(double antalLiter) {
        this.antalLiter -= antalLiter;
    }

    private void setAlkoholprocent() {
        double result = 0;
        for (Påfyldning påfyldning : påfyldninger) {
            result += påfyldning.getDestillering().getAlkoholProcent();
        }
        alkoholprocent = result / påfyldninger.size();
    }

    @Override
    public String toString() {
        return "Destillat{" +
                "påfyldning=" + påfyldninger +
                ", navn='" + navn + '\'' +
                ", antalLiter=" + antalLiter +
                ", påfyldningsDato=" + påfyldningsDato +
                '}';
    }
}
