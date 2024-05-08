package application.models;

import java.util.ArrayList;

public class Lager {
    private ArrayList<Reol> reoler = new ArrayList<>();
    private String navn;

    public Lager(String navn) {
        this.navn = navn;
    }

    public void addReol(int nummer, int pladser) {
        reoler.add(new Reol(nummer, pladser));
    }


    public ArrayList<Fad> getFadeKlar() {
        ArrayList<Fad> result = new ArrayList<>();
        for (Reol reol : reoler) {
            for (Hylde hylde : reol.getHylder()) {
                if (hylde.getFad().erWhiskyKlar()) {
                    result.add(hylde.getFad());
                }
            }
        }
        return result;
    }


}
