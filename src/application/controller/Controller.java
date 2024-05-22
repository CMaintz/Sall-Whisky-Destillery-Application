package application.controller;

import application.models.*;

import java.time.LocalDate;
import java.util.List;

public abstract class Controller {

    private static Storage storage;
    public static void setStorage(Storage storage) {Controller.storage = storage;}

    public static Fad createFad(int størrelse, String tidligereIndhold, String land, LocalDate fraÅr, LocalDate tilÅr, String leverandør) {
        Fad fad = new Fad(størrelse);
        fad.createFadHistorik(tidligereIndhold, land, fraÅr, tilÅr,  leverandør);
        storage.addFad(fad);
        return fad;
    }

    public static Korn createKorn(String sort, String variant, String markNavn) {
        Korn korn = new Korn(sort, variant, markNavn);
        storage.addKorntype(korn);
        return korn;
    }

    public static Destillering createDestillering(String maltBatch, Korn korn, String medarbejder, double mængdeVæske, double alkoholProcent, String rygeMateriale, String kommentar) {
        Destillering destillering = new Destillering(maltBatch, korn, medarbejder, mængdeVæske, alkoholProcent, rygeMateriale, kommentar);
        storage.addDestillering(destillering);
        return destillering;
    }

    public static Påfyldning createPåfyldning(String medarbejderNavn, double literPåFyldt, Destillering destillering, Destillat destillat) {
        Påfyldning påfyldning = destillat.createPåfyldning(medarbejderNavn, literPåFyldt, destillering);
        return påfyldning;
    }

    public static Destillat createDestillat() {
        Destillat destillat = new Destillat();
        return destillat;
    }

    public static Reol createReol(Lager lager, int antalHylder) {
        Reol reol = lager.createReol(antalHylder);
        return reol;
    }
    public static Lager createLager(String navn) {
        Lager lager = new Lager(navn);
        storage.addLager(lager);
        return lager;
    }

    public static FadTapning createFadTapning(String medarbejdernavn, Fad fad, WhiskyProdukt whiskyProdukt) {
        FadTapning ft = whiskyProdukt.createFadTapning(medarbejdernavn, fad.getDestillat().getAntalLiter(), fad);
        return ft;
    }

    public static WhiskyProdukt createWhiskyProdukt(String navn) {
        WhiskyProdukt whiskyProdukt = new WhiskyProdukt(navn);
        storage.addWhiskyProdukt(whiskyProdukt);
        return whiskyProdukt;
    }

    //    pre: antalFlasker <= currentLiterWhisky

    public static void createWhiskyflasker(WhiskyProdukt whiskyProdukt) {
        double liter = whiskyProdukt.getAntalLiter();
        String derp = "Her er en historie!";
//        String produktHistorie = whiskyProdukt.genererHistorie();
        for (int i = 0; i < liter; i++) {
            whiskyProdukt.createWhiskyFlaske(derp);
//            whiskyProdukt.createWhiskyFlaske(produktHistorie);
        }
        whiskyProdukt.setAntalLiter(0);
    }
    public static void omhældningAfDestillat(Fad fadFra, Fad fadTil) {
        fadFra.getDestillat().omhældDestillat(fadTil);
    }

    public static List<Lager> getLagre() {
        return storage.getLagre();
    }

    public static List<Fad> getFade() {
        return storage.getFade();
    }


    public static List<Destillering> getDestilleringer() {
        return storage.getDestilleringer();
    }

    public static List<Fad> getFyldtefade() {
        List<Fad> result = new ArrayList<>();
        for (Fad fad : Storage.getFade()) {
            if (fad.getDestillat() != null) {
                result.add(fad);
            }
        }
        return result;
    }

    public static List<WhiskyProdukt> getWhiskyProdukter() {
        return storage.getWhiskyProdukter();
    }

    public static List<Korn> getKorntyper() {
        return storage.getKorntyper();
    }

    public static List<Fad> getTommeFade() {
        List<Fad> result = new ArrayList<>();
        for (Fad fad : Storage.getFade()) {
            if (fad.getDestillat() == null) {
                result.add(fad);
            }
        }
        return result;
    }

}
