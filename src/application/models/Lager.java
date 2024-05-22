package application.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import application.models.Reol;
import application.models.Fad;
import application.models.Hylde;



public class Lager implements Serializable {
    private final List<Reol> reoler;
    private String navn;

    public Lager(String navn) {
        this.navn = navn;
        reoler = new ArrayList<>();
    }

    public Reol createReol(int antalHylder) {
        Reol toReturn = new Reol(antalHylder, reoler.size() + 1);
        reoler.add(toReturn);
        return toReturn;
    }

    public ArrayList<Reol> getReoler() {
        return new ArrayList<>(reoler);
    }

    public ArrayList<Reol> getReolerMedLedigPlads() {
        ArrayList<Reol> result = new ArrayList<>();
        for (Reol reol : reoler) {
            if (reol.getHylderUdenFad().length > 0) {
                result.add(reol);
            }
        }
        return result;
    }

public ArrayList<Fad> getFadeMedDestillat() {
        ArrayList<Fad> result = new ArrayList<>();
        for (Reol reol : reoler) {
            for (Hylde hylde : reol.getHylder()) {
                if (hylde.getFad() != null && hylde.getFad().getDestillat() != null) {
                    result.add(hylde.getFad());
                }
            }
        }
        return result;
    }


    public ArrayList<Fad> getFadeKlar() {
        ArrayList<Fad> result = new ArrayList<>();
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
