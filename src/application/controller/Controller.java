package application.controller;
import application.models.Destillering;
import application.models.Fad;
import application.models.Korn;
import storage.Storage;

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
}
