package application.controller;

import application.models.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Controller.
 */
public abstract class Controller {

    private static Storage storage;

    /**
     * Sets storage.
     *
     * @param storage the storage
     */
    public static void setStorage(Storage storage) {
        Controller.storage = storage;
    }

    /**
     * Create fad fad.
     *
     * @param størrelse        the størrelse
     * @param tidligereIndhold the tidligere indhold
     * @param land             the land
     * @param fraÅr            the fra år
     * @param leverandør       the leverandør
     * @return the fad
     */
    public static Fad createFad(int størrelse, String tidligereIndhold, String land, LocalDate fraÅr, String leverandør) {
        Fad fad = new Fad(størrelse);
        fad.createFadHistorik(tidligereIndhold, land, fraÅr, leverandør);
        storage.addFad(fad);
        return fad;
    }

    /**
     * Create korn korn.
     *
     * @param sort     the sort
     * @param variant  the variant
     * @param markNavn the mark navn
     * @return the korn
     */
    public static Korn createKorn(String sort, String variant, String markNavn) {
        Korn korn = new Korn(sort, variant, markNavn);
        storage.addKorntype(korn);
        return korn;
    }

    /**
     * Create destillering destillering.
     *
     * @param maltBatch      the malt batch
     * @param korn           the korn
     * @param medarbejder    the medarbejder
     * @param mængdeVæske    the mængde væske
     * @param alkoholProcent the alkohol procent
     * @param rygeMateriale  the ryge materiale
     * @param kommentar      the kommentar
     * @return the destillering
     */
    public static Destillering createDestillering(String maltBatch, Korn korn, String medarbejder, double mængdeVæske, double alkoholProcent, String rygeMateriale, String kommentar) {
        Destillering destillering = new Destillering(maltBatch, korn, medarbejder, mængdeVæske, alkoholProcent, rygeMateriale, kommentar);
        storage.addDestillering(destillering);
        return destillering;
    }

    /**
     * Create påfyldning påfyldning.
     *
     * @param medarbejderNavn the medarbejder navn
     * @param literPåFyldt    the liter på fyldt
     * @param destillering    the destillering
     * @param destillat       the destillat
     * @return the påfyldning
     */
    public static Påfyldning createPåfyldning(String medarbejderNavn, double literPåFyldt, Destillering destillering, Destillat destillat) {
        Påfyldning påfyldning = destillat.createPåfyldning(medarbejderNavn, literPåFyldt, destillering);
        return påfyldning;
    }

    /**
     * Create destillat destillat.
     *
     * @return the destillat
     */
    public static Destillat createDestillat() {
        Destillat destillat = new Destillat();
        return destillat;
    }

    /**
     * Create reol reol.
     *
     * @param lager       the lager
     * @param antalHylder the antal hylder
     * @return the reol
     */
    public static Reol createReol(Lager lager, int antalHylder) {
        Reol reol = lager.createReol(antalHylder);
        return reol;
    }

    /**
     * Create lager lager.
     *
     * @param navn the navn
     * @return the lager
     */
    public static Lager createLager(String navn) {
        Lager lager = new Lager(navn);
        storage.addLager(lager);
        return lager;
    }

    /**
     * Create fad tapning fad tapning.
     *
     * @param medarbejdernavn the medarbejdernavn
     * @param fad             the fad
     * @param whiskyProdukt   the whisky produkt
     * @return the fad tapning
     */
    public static FadTapning createFadTapning(String medarbejdernavn, Fad fad, WhiskyProdukt whiskyProdukt) {
        FadTapning ft = whiskyProdukt.createFadTapning(medarbejdernavn, fad.getDestillat().getAntalLiter(), fad);
        for (Lager lager : storage.getLagre()) {
            for (Reol reol : lager.getReoler()) {
                for (Hylde hylde : reol.getHylder()) {
                    if (hylde.getFad() != null && hylde.getFad().equals(fad)) {
                        hylde.fjernFad();
                        fad.removeDestillat();
                    }
                }
            }
        }
        return ft;
    }

    /**
     * Udregn total liter int.
     *
     * @param destillater the destillater
     * @param vand        the vand
     * @return the int
     */
    public static int udregnTotalLiter(List<Destillat> destillater, int vand) {
        int toReturn = 0;
        if (!destillater.isEmpty()) {
            for (Destillat destillat : destillater) {
                toReturn += destillat.getAntalLiter();
            }
        }
        return toReturn + vand;
    }

    /**
     * Udregn alkoholprocent double.
     *
     * @param destillater the destillater
     * @param vand        the vand
     * @return the double
     */
    public static double udregnAlkoholprocent(List<Destillat> destillater, int vand) {
        double literEthanol = 0;
        double antalLiter = vand;
        for (Destillat destillat : destillater) {
            literEthanol += (destillat.getAlkoholprocent() / 100) * destillat.getAntalLiter();
            antalLiter += destillat.getAntalLiter();
        }
        return (literEthanol / antalLiter) * 100;
    }

    /**
     * Create whisky produkt whisky produkt.
     *
     * @param navn the navn
     * @return the whisky produkt
     */
    public static WhiskyProdukt createWhiskyProdukt(String navn) {
        WhiskyProdukt whiskyProdukt = new WhiskyProdukt(navn);
        storage.addWhiskyProdukt(whiskyProdukt);
        return whiskyProdukt;
    }

    /**
     * Get fade med færdig destillat list.
     *
     * @return the list
     */
    public static List<Fad> getFadeMedFærdigDestillat() {
        ArrayList<Fad> toReturn = new ArrayList<>();
        for (Fad fad : getFyldtefade()) {
            if (fad.getDestillat().destillatKlar()) {
                toReturn.add(fad);
            }
        }
        return toReturn;
    }

    /**
     * Sets destillat start dato.
     *
     * @param destillat   the destillat
     * @param nyStartDato the ny start dato
     */
    public static void setDestillatStartDato(Destillat destillat, LocalDate nyStartDato) {
        destillat.setStartDato(nyStartDato);
    }

    /**
     * Gem produkt historie til fil.
     *
     * @param whiskyFlaske the whisky flaske
     */
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

    /**
     * Create whiskyflasker.
     *
     * @param whiskyProdukt the whisky produkt
     */
    public static void createWhiskyflasker(WhiskyProdukt whiskyProdukt) {
        double liter = whiskyProdukt.getAntalLiter();
        String produktHistorie = whiskyProdukt.genererHistorie();
        for (int i = 0; i < liter; i++) {
            whiskyProdukt.createWhiskyFlaske(produktHistorie);
        }
        whiskyProdukt.setAntalLiter(0);
    }

    /**
     * Sets destillerings starttid.
     *
     * @param destillering destilleringen
     * @param startTid     destilleringens starttid
     */
    public static void setDestilleringsStarttid(Destillering destillering, LocalDateTime startTid) {
        destillering.setStartTidspunkt(startTid);
    }

    /**
     * Omhældning af destillat.
     *
     * @param fadFra the fad fra
     * @param fadTil the fad til
     */
    public static void omhældningAfDestillat(Fad fadFra, Fad fadTil) {
        fadFra.getDestillat().omhældDestillat(fadTil);
    }

    /**
     * Gets lagre.
     *
     * @return the lagre
     */
    public static List<Lager> getLagre() {
        return storage.getLagre();
    }

    /**
     * Gets fade.
     *
     * @return the fade
     */
    public static List<Fad> getFade() {
        return storage.getFade();
    }


    /**
     * Gets destilleringer.
     *
     * @return the destilleringer
     */
    public static List<Destillering> getDestilleringer() {
        return storage.getDestilleringer();
    }

    /**
     * Gets fyldtefade.
     *
     * @return the fyldtefade
     */
    public static List<Fad> getFyldtefade() {
        List<Fad> result = new ArrayList<>();
        for (Fad fad : storage.getFade()) {
            if (fad.getDestillat() != null) {
                result.add(fad);
            }
        }
        return result;
    }

    /**
     * Gets whisky produkter.
     *
     * @return the whisky produkter
     */
    public static List<WhiskyProdukt> getWhiskyProdukter() {
        return storage.getWhiskyProdukter();
    }

    /**
     * Gets korntyper.
     *
     * @return the korntyper
     */
    public static List<Korn> getKorntyper() {
        return storage.getKorntyper();
    }

    /**
     * Gets tomme fade.
     *
     * @return the tomme fade
     */
    public static List<Fad> getTommeFade() {
        List<Fad> result = new ArrayList<>();
        for (Fad fad : storage.getFade()) {
            if (fad.getDestillat() == null) {
                result.add(fad);
            }
        }
        return result;
    }

    /**
     * Fad påfyldning.
     *
     * @param fad       the fad
     * @param destillat the destillat
     */
    public static void fadPåfyldning(Fad fad, Destillat destillat) {
        fad.addDestillat(destillat);
    }

    /**
     * Flyt fad.
     *
     * @param fad      the fad
     * @param hyldeFra the hylde fra
     * @param hyldeTil the hylde til
     */
    public static void flytFad(Fad fad, Hylde hyldeFra, Hylde hyldeTil) {
        hyldeFra.fjernFad();
        hyldeTil.placerFad(fad);
    }

    public static void removeFad(Fad fad) {
        storage.removeFad(fad);
    }

}
