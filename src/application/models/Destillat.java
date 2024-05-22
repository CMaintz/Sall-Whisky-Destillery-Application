package application.models;

import java.io.Serializable;
import java.text.DecimalFormat;
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
    public Påfyldning createPåfyldning(String medarbejderNavn, double literPåfyldt, Destillering destillering){
        Påfyldning pf = new Påfyldning(medarbejderNavn, literPåfyldt, destillering);
        påfyldninger.add(pf);
        antalLiter += pf.getLiterPåfyldt();
        udregnAlkoholprocent();
        return pf;
    }

    public void setStartDato(LocalDate startDato) {
        modningsHistorik.get(0).setStartDato(startDato);
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

    public Fad getFad() {
        return fad;
    }

    public List<ModningsHistorik> getModningsHistorik() {
        return new ArrayList<>(modningsHistorik);
    }

//    pre..?
    public void omhældDestillat(Fad newFad) {
            this.fad.removeDestillat();
            newFad.addDestillat(this);
    }

    private int getLiterPåfyldt() {
        int toReturn = 0;
        for (Påfyldning påfyldning : påfyldninger) {
            toReturn += påfyldning.getLiterPåfyldt();
        }
        return toReturn;
    }

    public void setFad(Fad fad) {
        this.fad = fad;
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
    }

    @Override
    public String toString() {
        DecimalFormat numberFormatter = new DecimalFormat("#.00");
        return numberFormatter.format(alkoholprocent) + "% Vol. " +  antalLiter + "L";
    }

    public String getDetaljer() {
        StringBuilder sb = new StringBuilder();
        sb.append("Påfyldninger: \n");
        for (Påfyldning påfyldning : påfyldninger) {
            sb.append(påfyldning.getDetaljer() + "\n");
            sb.append("\n");
        }
        return sb.toString();
    }
}
