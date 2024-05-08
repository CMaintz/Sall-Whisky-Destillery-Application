package application.controller;
import application.models.Fad;
import storage.Storage;

public class Controller {
    public static Fad createFad(int størrelse, int alder) {
        Fad fad = new Fad(størrelse, alder);
        Storage.addFad(fad);
        return fad;
    }
}
