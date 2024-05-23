package gui;

import application.controller.Controller;
import application.controller.Storage;
import application.models.*;
import javafx.application.Application;
import storage.ListStorage;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        Storage storage = ListStorage.loadStorage();
        if (storage == null) {
            storage = new ListStorage();
            System.out.println("Empty ListStorage created");
        }
        Controller.setStorage(storage);

        if (Controller.getFade().isEmpty()) {
            initStorage();
            System.out.println("Storage initialized");
        }

        Application.launch(LoginPane.class);

        ListStorage.saveStorage(storage);

    }

    /**
     * Initializes the storage with a handful of objects.
     */
    public static void initStorage() {
        Lager lade = Controller.createLager("Lars' lade");

        Reol ladeReol1 = Controller.createReol(lade, 3);
        Reol ladeReol2 = Controller.createReol(lade, 3);
        Reol ladeReol3 = Controller.createReol(lade, 3);

        Lager container = Controller.createLager("Baggårds container");

        Reol contReol1 = Controller.createReol(container, 3);
        Reol contReol2 = Controller.createReol(container, 3);

        Controller.createFad(200, "Sherry", "Spanien", LocalDate.of(2010, 1, 1), "Fadpusheren");
        Fad fad1 = Controller.createFad(40, "Sherry", "Spanien", LocalDate.of(2004, 1, 1), "Leverandørgutten");
        Fad fad2 = Controller.createFad(30, "Sherry", "Spanien", LocalDate.of(2001, 1, 1), "Leverandørgutten");
        Fad fad3 = Controller.createFad(20, "Rødvin", "Frankrig", LocalDate.of(2002, 1, 1), "Leverandørgutten");
        Fad fad4 = Controller.createFad(20, "Bourbon", "USA", LocalDate.of(20012, 1, 1), "Leverandørgutten");

        Korn korn1 = Controller.createKorn("Vårbyg", "Evergreen", "Highland og Stenhøj");
        Korn korn2 = Controller.createKorn("Vårbyg", "Laureate", "Stinna");
        Korn korn3 = Controller.createKorn("Vårbyg", "Focus", "Dagmarlund");

        Destillering destillering1 = Controller.createDestillering("Malthuset", korn1, "Chris", 1500, 81, "Bøgeflis", null);
        Destillering destillering2 = Controller.createDestillering("Malthuset", korn2, "Chris", 800, 50, null, null);
        Destillering destillering3 = Controller.createDestillering("Malthuset", korn3, "Chris", 5000, 71, "tørv", null);

        Destillat destillat1 = Controller.createDestillat();
        Destillat destillat2 = Controller.createDestillat();
        Destillat destillat3 = Controller.createDestillat();

        Controller.createPåfyldning("Maintz", 20, destillering1, destillat1);
        Controller.createPåfyldning("Maintz", 20, destillering2, destillat1);

        Controller.createPåfyldning("Maintz", 15, destillering1, destillat2);
        Controller.createPåfyldning("Maintz", 15, destillering3, destillat2);

        Controller.createPåfyldning("Maintz", 20, destillering3, destillat3);

        destillat1.createPåfyldning("Chris", 20, destillering1);

        fad1.addDestillat(destillat1);

        fad2.addDestillat(destillat2);

        fad3.addDestillat(destillat3);

        contReol1.addFad(fad1, 1);
        contReol1.addFad(fad2, 2);

        ladeReol1.addFad(fad3, 1);

        WhiskyProdukt tørv = Controller.createWhiskyProdukt("TØRV");
        WhiskyProdukt muld = Controller.createWhiskyProdukt("MULD");


        FadTapning ft1 = Controller.createFadTapning("Chris", fad1, tørv);
        FadTapning ft2 = Controller.createFadTapning("Chris", fad2, tørv);

        FadTapning ft3 = Controller.createFadTapning("Maintz", fad3, muld);

        Controller.createWhiskyflasker(muld);

    }


}
