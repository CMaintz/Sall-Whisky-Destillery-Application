package models;

import java.util.ArrayList;

public class Lager {
    ArrayList<Reol> reoler = new ArrayList<>();
    String navn;

    public Lager(String navn) {
        this.navn = navn;
    }

    public void addReol(int nummer, int pladser) {
        reoler.add(new Reol(nummer, pladser));
    }

    public void addFad(Reol reol, int plads, Fad fad) {
        if (reol.fade.get(plads - 1) == null) {
            reol.fade.add(plads - 1, fad);
        }
    }

//    public Fad getFadeKlar() {
//        for (Reol reol : reoler) {
//            for (Fad fad : reol.getFade()) {
//            }
//        }
//        return
//    }
}
