package application.controller;

import application.models.*;
import storage.Storage;

import java.time.LocalDate;

public class Controller {
    public static Fad createFad(int størrelse, String tidligereIndhold, String land, LocalDate fraÅr, String leverandør) {
        Fad fad = new Fad(størrelse);
        fad.createFadHistorik(tidligereIndhold, land, fraÅr, leverandør);
        Storage.addFad(fad);
        return fad;
    }

    public static Korn createKorn(String sort, String variant, String markNavn) {
        Korn korn = new Korn(sort, variant, markNavn);
        Storage.addKorn(korn);
        return korn;
    }

    public static Destillering createDestillering(String maltBatch, Korn korn, String medarbejder, double mængdeVæske, double alkoholProcent, String rygeMateriale, String kommentar) {
        Destillering destillering = new Destillering(maltBatch, korn, medarbejder, mængdeVæske, alkoholProcent, rygeMateriale, kommentar);
        Storage.addDestillering(destillering);
        return destillering;
    }

    public static Påfyldning createPåfyldning(String medarbejderNavn, double literPåFyldt, Destillering destillering, Destillat destillat) {
        Påfyldning påfyldning = destillat.createPåfyldning(medarbejderNavn, literPåFyldt, destillering);
        return påfyldning;
    }

    public static Destillat createDestilat(String navn) {
        Destillat destillat = new Destillat(navn);
        return destillat;
    }

    public static Lager createLager(String navn) {
        Lager lager = new Lager(navn);
        Storage.addLager(lager);
        return lager;
    }

    public static FadTapning createFadTapning(String medarbejdernavn, double literTappet, Fad fad, WhiskyProdukt whiskyProdukt) {
        FadTapning ft = whiskyProdukt.createFadTapning(medarbejdernavn, literTappet, fad);
        return ft;
    }

    public static WhiskyProdukt createWhiskyProdukt(String navn, String beskrivelse) {
        WhiskyProdukt whiskyProdukt = new WhiskyProdukt(navn);
        return whiskyProdukt;
    }

    //    pre: antalFlasker <= currentLiterWhisky

    public static void createWhiskyflasker(WhiskyProdukt whiskyProdukt) {
        double liter = whiskyProdukt.getAntalLiter();
        String produktHistorie = whiskyProdukt.genererHistorie();
        for (int i = 0; i < liter; i++) {
            whiskyProdukt.createWhiskyFlaske(produktHistorie);
        }
        whiskyProdukt.setAntalLiter(0);
    }
// TODO vi skal sørge for at kunne påfylde et fad så addDestillat kaldes
    public static void omhældningAfDestillat(Fad fadFra, Fad fadTil) {
        fadTil.addDestillat(fadFra.getDestillat());
        fadFra.setDestillat(null);
    }
    public static void removeLager(Lager lager) {
        boolean remove = true;
        for (Reol reol : lager.getReoler()) {
            for (Hylde hylde : reol.getHylder()) {
                if (hylde.getFad() != null) {
                    remove = false;
                }
            }
        }
        if (remove) {
            Storage.getLager().remove(lager);
        }
    }
}
