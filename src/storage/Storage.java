package storage;

import java.util.ArrayList;

import application.models.*;

public class Storage {
    private static ArrayList<Fad> fade = new ArrayList<>();
    private static ArrayList<Korn> korne = new ArrayList<>();
    private static ArrayList<Destillering> destilleringer = new ArrayList<>();
    private static ArrayList<Lager> lagere = new ArrayList<>();
    private static ArrayList<WhiskyFlaske> whiskyFlasker = new ArrayList<>();


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

    public static ArrayList<Lager> getLager() {
        return new ArrayList<>(lagere);
    }

    public static void addLager(Lager lager) {
        lagere.add(lager);
    }
    public static ArrayList<WhiskyFlaske> getWhiskyFlasker() {
        return new ArrayList<>(whiskyFlasker);
    }

    public static void addWhiskyFlaske(WhiskyFlaske whiskyFlaske) {
        whiskyFlasker.add(whiskyFlaske);
    }

}
