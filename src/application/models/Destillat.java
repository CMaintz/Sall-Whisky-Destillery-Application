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
    List<Omhældning> omhældninger;
    private Fad fad;

    public Destillat(String navn) {
        påfyldninger = new ArrayList<>();
        omhældninger = new ArrayList<>();

        this.navn = navn;
        påfyldningsDato = LocalDate.now();
    }

    public Påfyldning createPåfyldning(String medarbejderNavn, double literPåfyldt, Destillering destillering){
        Påfyldning pf = new Påfyldning(medarbejderNavn, literPåfyldt, destillering);
        påfyldninger.add(pf);
        antalLiter += pf.getLiterPåfyldt();
        udregnAlkoholprocent();
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

    public List<Omhældning> getOmhældninger() {
        return omhældninger;
    }

    public void omhældDestillat(Fad newFad) {
        Omhældning omhældning = new Omhældning(fad, påfyldningsDato, LocalDate.now());
        this.omhældninger.add(omhældning);
        fad.setDestillat(null);
        this.fad = newFad;
    }

    private void udregnAlkoholprocent() {
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
        double days = 0;
        boolean result = false;
//        TODO if omhældninger.size > 0?
        for (Omhældning dh : omhældninger) {
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
