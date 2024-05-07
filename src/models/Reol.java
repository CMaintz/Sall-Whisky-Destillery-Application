package models;

import java.util.ArrayList;

public class Reol {
    int nummer;
    int pladser;
    ArrayList<Fad> fade = new ArrayList<>();

    public Reol(int nummer, int pladser) {
        this.nummer = nummer;
        this.pladser = pladser;
    }

    public int getPladser() {
        return pladser;
    }

    public ArrayList<Fad> getFade() {
        return fade;
    }

    public Fad getFadPlads(int plads) {
        return fade.get(plads - 1);
    }
}
