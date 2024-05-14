package application.models;

import java.util.ArrayList;
import application.models.Reol;
import application.models.Fad;
import application.models.Hylde;



public class Lager {
    private ArrayList<Reol> reoler = new ArrayList<>();
    private String navn;

    public Lager(String navn) {
        this.navn = navn;
    }

    public ArrayList<Reol> getReoler() {
        return reoler;
    }

    public void createReol(int nummer, int pladser) {
        reoler.add(new Reol(nummer, pladser));
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


}
