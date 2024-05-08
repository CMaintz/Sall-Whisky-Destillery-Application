package application.models;

public class Reol {
    private int nummer;
    private Hylde[] hylder;

    public Reol(int nummer, int pladser) {
        this.nummer = nummer;
        this.hylder = new Hylde[pladser-1];
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
            hylder[plads-1].setFad(fad);
        }
    }
}
