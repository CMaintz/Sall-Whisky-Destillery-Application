package application.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Reol.
 */
public class Reol implements Serializable {
    private int reolNummer;
    private Hylde[] hylder;

    /**
     * Instantiates a new Reol.
     *
     * @param antalHylder the antal hylder
     * @param reolNummer  the reol nummer
     */
    Reol(int antalHylder, int reolNummer) {
        this.reolNummer = reolNummer;
        this.hylder = new Hylde[antalHylder];

        for (int i = 0; i < antalHylder; i++) {
            hylder[i] = new Hylde(i + 1);
        }
    }

    /**
     * Get hylder uden fad hylde [ ].
     *
     * @return the hylde [ ]
     */
    public Hylde[] getHylderUdenFad() {
        List<Hylde> hylderUdenFad = new ArrayList<>();
        for (Hylde hylde : hylder) {
            if (hylde.getFad() == null) {
                hylderUdenFad.add(hylde);
            }
        }
        return hylderUdenFad.toArray(new Hylde[0]);
    }

    /**
     * Get hylder hylde [ ].
     *
     * @return the hylde [ ]
     */
    public Hylde[] getHylder() {
        return hylder;
    }

    /**
     * Get alle fade hylde [ ].
     *
     * @return the hylde [ ]
     */
    public Hylde[] getAlleFade() {
        Hylde[] result = new Hylde[hylder.length];
        for (int i = 0; i < hylder.length; i++) {
            if (hylder[i] != null) {
                result[i] = hylder[i];
            }
        }
        return result;
    }

    /**
     * Get fad på hylde fad.
     *
     * @param plads the plads
     * @return the fad
     */
    public Fad getFadPåHylde(int plads) {
        return hylder[plads-1].getFad();
    }

    /**
     * Add fad.
     *
     * @param fad   the fad
     * @param plads the plads
     */
    public void addFad(Fad fad, int plads) {
        if (hylder[plads-1].getFad() == null) {
            hylder[plads-1].placerFad(fad);
        }
    }

    @Override
    public String toString() {
        return "Reol: " + reolNummer;
    }
}
