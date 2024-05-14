package application.controller;
import application.models.*;
import storage.Storage;

import java.util.ArrayList;

public class Controller {
    public static Fad createFad(int størrelse, int alder, FadHistorik fadHistorik) {
        Fad fad = new Fad(størrelse, alder, fadHistorik);
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

    public static Påfyldning createPåfyldning(String medarbejderNavn, double literPåFyldt, Destillering destillering) {
        Påfyldning påfyldning = new Påfyldning(medarbejderNavn, literPåFyldt, destillering);
        return påfyldning;
    }

    public static Destillat createDestilat(ArrayList<Påfyldning> påfyldninger, String navn) {
        Destillat destillat = new Destillat(påfyldninger, navn);
        return destillat;
    }

    public static Lager createLager(String navn) {
        Lager lager = new Lager(navn);
        Storage.addLager(lager);
        return lager;
    }
    
    public static FadTapning createFadTapning(String medarbejdernavn, double literTappet, Fad fad) {
        FadTapning fadTapning = new FadTapning(medarbejdernavn, literTappet, fad);
        return fadTapning;
    }

    public static WhiskyProdukt createWhiskyProdukt(String navn, ArrayList<FadTapning> fadTapninger, double alkoholprocent, String beskrivelse, String type) {
        WhiskyProdukt whiskyProdukt = new WhiskyProdukt(navn, fadTapninger, beskrivelse, type);
        return whiskyProdukt;
    }
    
    public static WhiskyFlaske createWhiskyFlaske(int nummer, WhiskyProdukt whiskyProdukt) {
        WhiskyFlaske whiskyFlaske = new WhiskyFlaske(nummer, whiskyProdukt);
        Storage.addWhiskyFlaske(whiskyFlaske);
        return whiskyFlaske;
    }

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
//            ?????
        }
    }
}
