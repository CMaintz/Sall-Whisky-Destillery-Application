package application.models;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class Destillat {
    private ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
    private String navn;
    private double antalLiter;
    private LocalDate påfyldningsDato;
    private double alkoholprocent;
    ArrayList<DestillatHistorik> destillatHistorik = new ArrayList<>();
    private Fad fad;

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

    public void setFad(Fad fad) {
        this.fad = fad;
    }
    

    public void addDestillatHistorik() {
        DestillatHistorik destillatHistorik = new DestillatHistorik(fad, påfyldningsDato, LocalDate.now(), this);
        this.destillatHistorik.add(destillatHistorik);
    }

    private void setAlkoholprocent() {
        double result = 0;
        for (Påfyldning påfyldning : påfyldninger) {
            result += påfyldning.getDestillering().getAlkoholProcent();
        }
        alkoholprocent = result / påfyldninger.size();
    }

    public boolean destillatKlar() {
        double days = 0;
        boolean result = false;
        for (DestillatHistorik dh : destillatHistorik) {
            Period period = Period.between(dh.getStartDato(), dh.getSlutDato());
            days += period.getDays();
        }
        if (days >= 1095) {
            result = true;
        }
        return result;
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
