package storage;

import java.util.ArrayList;

import application.models.*;

public class Storage {
    private static ArrayList<Fad> fade = new ArrayList<>();
    private static ArrayList<Korn> korne = new ArrayList<>();
    private static ArrayList<Destillering> destilleringer = new ArrayList<>();
    private static ArrayList<Destillat> destillater = new ArrayList<>();
    private static ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
    private static ArrayList<Lager> lagere = new ArrayList<>();
    private static ArrayList<WhiskyProdukt> whiskyProdukter = new ArrayList<>();
    private static ArrayList<FadTapning> fadTapninger = new ArrayList<>();




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
    public static ArrayList<Destillat> getDestillater() {
        return new ArrayList<>(destillater);
    }

    public static void addDestillat(Destillat destillat) {
        destillater.add(destillat);
    }
    public static ArrayList<Påfyldning> getPåfyldninger() {
        return new ArrayList<>(påfyldninger);
    }

    public static void addPåfyldninger(Påfyldning påfyldning) {
        påfyldninger.add(påfyldning);
    }

    public static ArrayList<Lager> getLager() {
        return new ArrayList<>(lagere);
    }

    public static void addLager(Lager lager) {
        lagere.add(lager);
    }
    public static ArrayList<WhiskyProdukt> getWhiskyProdukter() {
        return new ArrayList<>(whiskyProdukter);
    }

    public static void addWhiskyProdukt(WhiskyProdukt whiskyProdukt) {
        whiskyProdukter.add(whiskyProdukt);
    }
    public static ArrayList<FadTapning> getFadTapninger() {
        return new ArrayList<>(fadTapninger);
    }

    public static void addFadTapning(FadTapning fadTapning) {
        fadTapninger.add(fadTapning);
    }

}
