package application.models;

import java.time.LocalDate;
import java.time.Period;
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
    }

    // Pre: literPåfyld > 0
    public Påfyldning createPåfyldning(String medarbejderNavn, double literPåfyldt, Destillering destillering){
        Påfyldning pf = new Påfyldning(medarbejderNavn, literPåfyldt, destillering);
        påfyldninger.add(pf);
        antalLiter += pf.getLiterPåfyldt();
        setAlkoholprocent();
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

    public void setPåfyldningsDato(LocalDate påfyldningsDato) {
        this.påfyldningsDato = påfyldningsDato;
    }

    public Fad getFad() {
        return fad;
    }

    public List<DestillatHistorik> getDestillatHistorik() {
        return destillatHistorik;
    }

    //Pre : this.fad != null
    public void addDestillatHistorik(Fad newFad) {
        DestillatHistorik destillatHistorik = new DestillatHistorik(fad, påfyldningsDato, LocalDate.now());
        påfyldningsDato = LocalDate.now();
        this.destillatHistorik.add(destillatHistorik);
        fad.setDestillat(null);
    }

    private void setAlkoholprocent() {
        double literEthanol = 0;
        for (Påfyldning påfyldning : påfyldninger) {
            literEthanol += (påfyldning.getDestillering().getAlkoholProcent() / 100) * påfyldning.getLiterPåfyldt();
        }
        alkoholprocent = (literEthanol / antalLiter) * 100;
    }

    public boolean destillatKlar() {
        Period periodPD = Period.between(påfyldningsDato, LocalDate.now());
        if (periodPD.getYears() >= 3) {
            return true;
        }
        if (destillatHistorik != null) {
            Period period = Period.between(destillatHistorik.get(0).getStartDato(), LocalDate.now());
            if (period.getYears() >= 3) {
                return true;
            }
        }
        return false;
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
