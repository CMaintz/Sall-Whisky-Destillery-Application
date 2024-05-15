package application.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Destillat {
    private List<Påfyldning> påfyldninger;
    private String navn;
    private double antalLiter;
    private LocalDate påfyldningsDato;
    private double alkoholprocent;
    List<DestillatHistorik> destillatHistorik;
    private Fad fad;

    public Destillat(String navn) {
        påfyldninger = new ArrayList<>();
        destillatHistorik = new ArrayList<>();

        this.navn = navn;
        påfyldningsDato = LocalDate.now();
        setAlkoholprocent();
    }

    public Påfyldning createPåfyldning(String medarbejderNavn, double literPåfyldt, Destillering destillering){
        Påfyldning pf = new Påfyldning(medarbejderNavn, literPåfyldt, destillering);
        påfyldninger.add(pf);
        antalLiter += pf.getLiterPåfyldt();
        return pf;
    }
    public ArrayList<Påfyldning> getPåfyldninger() {
        return new ArrayList<>(påfyldninger);
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

    public void fjernAntalLiter(double antalLiter) {
        this.antalLiter -= antalLiter;
    }

    public void setFad(Fad fad) {
        this.fad = fad;
    }
    

    public void addDestillatHistorik() {
        DestillatHistorik destillatHistorik = new DestillatHistorik(fad, påfyldningsDato, LocalDate.now());
        this.destillatHistorik.add(destillatHistorik);
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
