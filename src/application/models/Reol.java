package application.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Reol implements Serializable {
    private int reolNummer;
    private Hylde[] hylder;

    Reol(int antalHylder, int reolNummer) {
        this.reolNummer = reolNummer;
        this.hylder = new Hylde[antalHylder];

        for (int i = 0; i < antalHylder; i++) {
            hylder[i] = new Hylde(i + 1);
        }
    }

    public Hylde[] getHylderUdenFad() {
        List<Hylde> hylderUdenFad = new ArrayList<>();
        for (Hylde hylde : hylder) {
            if (hylde.getFad() == null) {
                hylderUdenFad.add(hylde);
            }
        }
        return hylderUdenFad.toArray(new Hylde[0]);
    }

    public Hylde[] getHylder() {
        return hylder;
    }

    public Hylde[] getAlleFade() {
        Hylde[] result = new Hylde[hylder.length];
        for (int i = 0; i < hylder.length; i++) {
            if (hylder[i] != null) {
                result[i] = hylder[i];
            }
        }
        return result;
    }

    public Fad getFadPåHylde(int plads) {
        return hylder[plads-1].getFad();
    }

    public void addFad(Fad fad, int plads) {
        if (hylder[plads-1] == null) {
            hylder[plads-1].placerFad(fad);
        }
    }
}
