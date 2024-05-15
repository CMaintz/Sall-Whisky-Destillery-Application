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
        this.navn = navn;
        påfyldningsDato = LocalDate.now();
        påfyldninger = new ArrayList<>();
        destillatHistorik = new ArrayList<>();
    }

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


    public void addDestillatHistorik() {
        this.destillatHistorik.add(new DestillatHistorik(fad, påfyldningsDato, LocalDate.now()));
    }

    private void setAlkoholprocent() {
        double literEthanol = 0;
        for (Påfyldning påfyldning : påfyldninger) {
            literEthanol += (påfyldning.getDestillering().getAlkoholProcent() / 100) * påfyldning.getLiterPåfyldt();
        }
        alkoholprocent = (literEthanol / antalLiter) * 100;
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
