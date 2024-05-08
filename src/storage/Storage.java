package storage;

import java.util.ArrayList;

import application.models.Destillering;
import application.models.Fad;
import application.models.Korn;
import application.models.Påfyldning;

public class Storage {
    private static ArrayList<Fad> fade = new ArrayList<>();
    private static ArrayList<Korn> korne = new ArrayList<>();
    private static ArrayList<Destillering> destilleringer = new ArrayList<>();


    public static ArrayList<Fad> getFade() {
        return new ArrayList<>(fade);
    }

    public static void addFad(Fad fad) {
        fade.add(fad);
    }

    public static ArrayList<Korn> getKorn() {
        return new ArrayList<>(korne);
    }

    public static void addKorn(Korn korn) {
        korne.add(korn);
    }

    public static ArrayList<Destillering> getDestillering() {
        return new ArrayList<>(destilleringer);
    }

    public static void addDestillering(Destillering destillering) {
        destilleringer.add(destillering);
    }

}
