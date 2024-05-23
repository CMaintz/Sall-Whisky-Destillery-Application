package application.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import application.models.Reol;
import application.models.Fad;
import application.models.Hylde;


/**
 * The type Lager.
 */
public class Lager implements Serializable {
    private final List<Reol> reoler;
    private String navn;

    /**
     * Instantiates a new Lager.
     *
     * @param navn the navn
     */
    public Lager(String navn) {
        this.navn = navn;
        reoler = new ArrayList<>();
    }

    /**
     * Create reol reol.
     *
     * @param antalHylder the antal hylder
     * @return the reol
     */
    public Reol createReol(int antalHylder) {
        Reol toReturn = new Reol(antalHylder, reoler.size() + 1);
        reoler.add(toReturn);
        return toReturn;
    }

    /**
     * Gets reoler.
     *
     * @return the reoler
     */
    public ArrayList<Reol> getReoler() {
        return new ArrayList<>(reoler);
    }

    /**
     * Gets reoler med ledig plads.
     *
     * @return the reoler med ledig plads
     */
    public List<Reol> getReolerMedLedigPlads() {
        List<Reol> result = new ArrayList<>();
        for (Reol reol : reoler) {
            if (reol.getHylderUdenFad().length > 0) {
                result.add(reol);
            }
        }
        return result;
    }

    /**
     * Gets fade med destillat.
     *
     * @return the fade med destillat
     */
    public List<Fad> getFadeMedDestillat() {
        List<Fad> result = new ArrayList<>();
        for (Reol reol : reoler) {
            for (Hylde hylde : reol.getHylder()) {
                if (hylde.getFad() != null && hylde.getFad().getDestillat() != null) {
                    result.add(hylde.getFad());
                }
            }
        }
        return result;
    }


    /**
     * Gets fade klar.
     *
     * @return the fade klar
     */
    public List<Fad> getFadeKlar() {
        List<Fad> result = new ArrayList<>();
        for (Reol reol : reoler) {
            for (Hylde hylde : reol.getHylder()) {
                if (hylde.getFad().getDestillat().destillatKlar()) {
                    result.add(hylde.getFad());
                }
            }
        }
        return result;
    }

    //toString
    public String toString() {
        return "Lager: " + navn;
    }


}
