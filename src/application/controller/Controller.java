package application.controller;
import application.models.*;
import storage.Storage;

import java.util.ArrayList;

public class Controller {
    public static Fad createFad(int størrelse, int alder) {
        Fad fad = new Fad(størrelse, alder);
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
        Storage.addPåfyldninger(påfyldning);
        return påfyldning;
    }

    public static Destillat createDestilat(ArrayList<Påfyldning> påfyldninger, String navn) {
        Destillat destillat = new Destillat(påfyldninger, navn);
        Storage.addDestillat(destillat);
        return destillat;
    }

}
