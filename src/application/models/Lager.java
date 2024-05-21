package application.models;

import java.util.ArrayList;
import application.models.Reol;
import application.models.Fad;
import application.models.Hylde;



public class Lager {
    private final ArrayList<Reol> reoler = new ArrayList<>();
    private int antalReoler;
    private String navn;

    public Lager(String navn) {
        this.navn = navn;
    }

    public Reol createReol(int antalHylder) {
        antalReoler++;
        Reol toReturn = new Reol(antalHylder, antalReoler);
        reoler.add(toReturn);
        return toReturn;
    }

    public ArrayList<Reol> getReoler() {
        return new ArrayList<>(reoler);
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
