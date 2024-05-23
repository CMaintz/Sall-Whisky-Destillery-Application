package application.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Destillat implements Serializable {
    private final List<Påfyldning> påfyldninger;
    private double antalLiter;
    private double alkoholprocent;
    private final List<ModningsHistorik> modningsHistorik;
    private Fad fad;

    public Destillat() {
        påfyldninger = new ArrayList<>();
        modningsHistorik = new ArrayList<>();
    }

    // Pre: literPåfyld > 0
    public Påfyldning createPåfyldning(String medarbejderNavn, double literPåfyldt, Destillering destillering) {
        if (literPåfyldt <= 0 || literPåfyldt > destillering.getAntalLiter()) {
            throw new IllegalArgumentException("Invalid volume for påfyldning.");
        }

        Påfyldning pf = new Påfyldning(medarbejderNavn, literPåfyldt, destillering);
        påfyldninger.add(pf);
        antalLiter += pf.getLiterPåfyldt();
        udregnAlkoholprocent();
        return pf;
    }

    public ArrayList<Påfyldning> getPåfyldninger() {
        return new ArrayList<>(påfyldninger);
    }

    public LocalDate getPåfyldningsDato() {
        return modningsHistorik.get(0).getStartDato();
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
        createModningsHistorik();
    }

    public Fad getFad() {
        return fad;
    }

    public List<ModningsHistorik> getModningsHistorik() {
        return modningsHistorik;
    }

    public void omhældDestillat(Fad newFad) {
            fad.setDestillat(null);
            this.fad = newFad;
            newFad.addDestillat(this);
            createModningsHistorik();
    }

    private ModningsHistorik createModningsHistorik() {
        if (this.modningsHistorik.size() > 0) {
            this.modningsHistorik.get(this.modningsHistorik.size() - 1).setSlutDato(LocalDate.now());
        }
        ModningsHistorik modningsHistorik = new ModningsHistorik(fad, LocalDate.now());
        this.modningsHistorik.add(modningsHistorik);
        return modningsHistorik;
    }

    private void udregnAlkoholprocent() {
        double literEthanol = 0;
        for (Påfyldning påfyldning : påfyldninger) {
            literEthanol += (påfyldning.getDestillering().getAlkoholProcent() / 100) * påfyldning.getLiterPåfyldt();
        }
        alkoholprocent = (literEthanol / antalLiter) * 100;
    }

    public boolean destillatKlar() {
        Period periodPD = Period.between(modningsHistorik.get(0).getStartDato(), LocalDate.now());
        if (periodPD.getYears() >= 3) {
            return true;
        }
        return false;
//        double days = 0;
//        boolean result = false;
////        TODO if omhældninger.size > 0?
//        for (ModningsHistorik dh : modningsHistorik) {
//            Period period = Period.between(dh.getStartDato(), dh.getSlutDato());
//            days += period.getDays();
//        }
//        if (days >= 1095) {
//            result = true;
//        }
//        return result;
    }

    @Override
    public String toString() {
        return "Destillat{" +
                "påfyldning=" + påfyldninger +
                ", antalLiter=" + antalLiter +
                '}';
    }
}
