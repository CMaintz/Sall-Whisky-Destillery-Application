package application.controller;

import application.models.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Controller {

    private static Storage storage;

    public static void setStorage(Storage storage) {
        Controller.storage = storage;
    }

    public static Fad createFad(int størrelse, String tidligereIndhold, String land, LocalDate fraÅr, String leverandør) {
        Fad fad = new Fad(størrelse);
        fad.createFadHistorik(tidligereIndhold, land, fraÅr, leverandør);
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
        ArrayList<Lager> lagre = new ArrayList<>(storage.getLagre());
        // TODO fadet skal fjernes fra lageret (hylden) når createFadTapning kaldes, og destillatet skal fjernes fra fadet.
//        boolean fadFundet = false;
//            for (int i = 0; i < lagre.size(); i++) {
//                Lager lager = lagre.get(i);
//                for (int j = 0; j < lager.getReoler().size(); j++) {
//                    Reol reol = lager.getReoler().get(i);
//                    for (int k = 0; k < reol.getHylder().length; k++) {
//                        fadFundet = reol.getHylder()[k].getFad() == fad;
//                        if (fadFundet) {
//                            reol.getHylder()[k].fjernFad();
//                            fad.removeDestillat();
//                        }
//                    }
//                }
//
//            }
        return ft;
    }

    public static int udregnTotalLiter(List<Destillat> destillater, int vand) {
        int toReturn = 0;
        if (!destillater.isEmpty()) {
            for (Destillat destillat : destillater) {
                toReturn += destillat.getAntalLiter();
            }
        }
        return toReturn + vand;
    }

    public static double udregnAlkoholprocent(List<Destillat> destillater, int vand) {
        double literEthanol = 0;
        double antalLiter = vand;
        for (Destillat destillat : destillater) {
            literEthanol += (destillat.getAlkoholprocent() / 100) * destillat.getAntalLiter();
            antalLiter += destillat.getAntalLiter();
        }
        return (literEthanol / antalLiter) * 100;
    }

    public static WhiskyProdukt createWhiskyProdukt(String navn) {
        WhiskyProdukt whiskyProdukt = new WhiskyProdukt(navn);
        storage.addWhiskyProdukt(whiskyProdukt);
        return whiskyProdukt;
    }

    public static List<Fad> getFadeMedFærdigDestillat() {
        ArrayList<Fad> toReturn = new ArrayList<>();
        for (Fad fad : getFyldtefade()) {
            if (fad.getDestillat().destillatKlar()) {
                toReturn.add(fad);
            }
        }
        return toReturn;
    }

    public static void setDestillatStartDato(Destillat destillat, LocalDate nyStartDato) {
        destillat.setStartDato(nyStartDato);
    }

    public static void gemProduktHistorieTilFil(WhiskyFlaske whiskyFlaske) {
        String fileName = whiskyFlaske.getWhisky().getNavn() + "Historie.txt";
        try {
            PrintWriter writer = new PrintWriter((fileName));
            writer.print(whiskyFlaske.getProduktHistorie());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static void setDestilleringsStarttid(Destillering destillering, LocalDateTime startTid) {
        destillering.setStartDato(startTid);
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
        for (Fad fad : storage.getFade()) {
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
        for (Fad fad : storage.getFade()) {
            if (fad.getDestillat() == null) {
                result.add(fad);
            }
        }
        return result;
    }

    public static void fadPåfyldning(Fad fad, Destillat destillat) {
        fad.addDestillat(destillat);
    }
    public static void flytFad(Fad fad, Hylde hyldeFra, Hylde hyldeTil) {
        hyldeFra.fjernFad();
        hyldeTil.placerFad(fad);
    }

}
