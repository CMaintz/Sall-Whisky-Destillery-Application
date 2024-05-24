package application.models;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Destillat.
 */
public class Destillat implements Serializable {
    private final List<Påfyldning> påfyldninger;
    private double antalLiter;
    private double alkoholprocent;
    private final List<ModningsHistorik> modningsHistorik;
    private Fad fad;

    /**
     * Instantiates a new Destillat.
     */
    public Destillat() {
        påfyldninger = new ArrayList<>();
        modningsHistorik = new ArrayList<>();
    }

    /**
     * Create påfyldning påfyldning.
     *
     * @param medarbejderNavn the medarbejder navn
     * @param literPåfyldt    the liter påfyldt
     * @param dest            the destillering
     * @return the påfyldning
     */
    public Påfyldning createPåfyldning(String medarbejderNavn, double literPåfyldt, Destillering dest) {
        if (literPåfyldt <= 0 || literPåfyldt > dest.getAntalLiter()) {
            throw new IllegalArgumentException("Invalid volume for påfyldning.");
        }

        Påfyldning pf = new Påfyldning(medarbejderNavn, literPåfyldt, dest);
        påfyldninger.add(pf);
        antalLiter += pf.getLiterPåfyldt();
        udregnAlkoholprocent();
        return pf;
    }

    /**
     * Sets start dato.
     *
     * @param startDato the start dato
     */
    public void setStartDato(LocalDate startDato) {
        modningsHistorik.get(0).setPåfyldningsDato(startDato);
    }

    /**
     * Get påfyldninger array list.
     *
     * @return the array list
     */
    public ArrayList<Påfyldning> getPåfyldninger() {
        return new ArrayList<>(påfyldninger);
    }

    /**
     * Get påfyldnings dato local date.
     *
     * @return the local date
     */
    public LocalDate getPåfyldningsDato() {
        return modningsHistorik.get(0).getPåfyldningsDato();
    }

    /**
     * Gets alkoholprocent.
     *
     * @return the alkoholprocent
     */
    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    /**
     * Gets antal liter.
     *
     * @return the antal liter
     */
    public double getAntalLiter() {
        return antalLiter;
    }

    /**
     * Fjern antal liter.
     *
     * @param antalLiter the antal liter
     */
    public void fjernAntalLiter(double antalLiter) {
        this.antalLiter -= antalLiter;
    }

    /**
     * Gets fad.
     *
     * @return the fad
     */
    public Fad getFad() {
        return fad;
    }

    /**
     * Gets modnings historik.
     *
     * @return the modnings historik
     */
    public List<ModningsHistorik> getModningsHistorik() {
        return new ArrayList<>(modningsHistorik);
    }

    /**
     * Omhæld destillat.
     *
     * @param newFad the new fad
     */
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

    /**
     * Sets fad.
     *
     * @param fad the fad
     */
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

    /**
     * Destillat klar boolean.
     *
     * @return the boolean
     */
    public boolean destillatKlar() {
        Period periodPD = Period.between(modningsHistorik.get(0).getPåfyldningsDato(), LocalDate.now());
        if (periodPD.getYears() >= 3) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        DecimalFormat numberFormatter = new DecimalFormat("#.00");
        return numberFormatter.format(alkoholprocent) + "% Vol. " + antalLiter + "L";
    }

    /**
     * Gets detaljer.
     *
     * @return the detaljer
     */
    public String getDetaljer() {
        StringBuilder sb = new StringBuilder();
        sb.append("Påfyldninger: \n");
        for (Påfyldning påfyldning : påfyldninger) {
            sb.append(påfyldning.getDetaljer() + "\n");
            sb.append("\n");
        }
        sb.append("Modningshistorik: \n");
        for (ModningsHistorik modningsHistorik : modningsHistorik) {
            sb.append("Modnet i " + modningsHistorik.getPåfyldningsDato().until(modningsHistorik.getSlutDato().plusDays(1)).getMonths() + " måneder i fad:\n");
            sb.append(modningsHistorik.getFad().toString() + " \n" + modningsHistorik.getFad().getAlder() + "år gammelt");
        }
        return sb.toString();
    }
}
