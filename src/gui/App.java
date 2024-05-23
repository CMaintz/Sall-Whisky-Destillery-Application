package gui;

import application.controller.Controller;
import application.controller.Storage;
import application.models.*;
import javafx.application.Application;
import storage.ListStorage;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) {
        Storage storage = ListStorage.loadStorage();
//        if (storage == null) {
            storage = new ListStorage();
            System.out.println("Empty ListStorage created");
//        }
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

        Fad fad1 = Controller.createFad(40, "Sherry", "Spanien", LocalDate.of(2004, 1, 1), "Fadpusheren");
        Fad fad2 = Controller.createFad(30, "Sherry", "Spanien", LocalDate.of(2001, 1, 1), "Fadpusheren");
        Fad fad3 = Controller.createFad(20, "Rødvin", "Frankrig", LocalDate.of(2002, 1, 1), "Le leverandeur");
        Fad fad4 = Controller.createFad(20, "Bourbon", "USA", LocalDate.of(20012, 1, 1), "Yeehaw leverandør");
        Fad fad5 = Controller.createFad(20, "Bourbon", "USA", LocalDate.of(20012, 1, 1), "Yeehaw leverandør");
        Fad fad6 = Controller.createFad(20, "Rødvin", "Frankrig", LocalDate.of(2000, 1, 1), "Le leverandeur");
        Fad fad7 = Controller.createFad(50, "Rødvin", "Frankrig", LocalDate.of(2000, 1, 1), "Le leverandeur");
        Fad fad8 = Controller.createFad(50, "Rødvin", "Frankrig", LocalDate.of(1999, 1, 1), "Le leverandeur");
        Fad fad9 = Controller.createFad(50, "Rødvin", "Frankrig", LocalDate.of(1995, 1, 1), "Le leverandeur");
        Fad fad10 = Controller.createFad(50, "Sherry", "Spanien", LocalDate.of(2009, 1, 1), "Fadpusheren");

        Korn korn1 = Controller.createKorn("Vårbyg", "Evergreen", "Highland og Stenhøj");
        Korn korn2 = Controller.createKorn("Vårbyg", "Laureate", "Mosevang og Stadsgaard");
        Korn korn3 = Controller.createKorn("Vårbyg", "Focus", "Dagmarlund og Skovsø");

        Destillering destillering1 = Controller.createDestillering("Malthuset", korn1, "Chris", 1500, 81, "Bøgeflis", null);
        Destillering destillering2 = Controller.createDestillering("Malthuset", korn2, "Chris", 800, 50, null, null);
        Destillering destillering3 = Controller.createDestillering("Malthuset", korn3, "Chris", 5000, 55, "tørverøget", null);

        Controller.setDestilleringsStarttid(destillering1, LocalDateTime.now().minusHours(127));
        Controller.setDestilleringsStarttid(destillering2, LocalDateTime.now().minusHours(152));
        Controller.setDestilleringsStarttid(destillering3, LocalDateTime.now().minusHours(171));

        Destillat destillat1 = Controller.createDestillat();
        Destillat destillat2 = Controller.createDestillat();
        Destillat destillat3 = Controller.createDestillat();
        Destillat destillat4 = Controller.createDestillat();
        Destillat destillat5 = Controller.createDestillat();
        Destillat destillat6 = Controller.createDestillat();
        Destillat destillat7 = Controller.createDestillat();
        Destillat destillat8 = Controller.createDestillat();

        Controller.createPåfyldning("Maintz", 20, destillering1, destillat1);
        Controller.createPåfyldning("Maintz", 20, destillering2, destillat1);

        Controller.createPåfyldning("Maintz", 15, destillering1, destillat2);
        Controller.createPåfyldning("Maintz", 15, destillering3, destillat2);

        Controller.createPåfyldning("Maintz", 20, destillering3, destillat3);
        Controller.createPåfyldning("Maintz", 20, destillering3, destillat4);

        Controller.createPåfyldning("Chris", 20, destillering2, destillat5);
        Controller.createPåfyldning("Chris", 50, destillering2, destillat6);

        Controller.createPåfyldning("Alex", 50, destillering2, destillat7);
        Controller.createPåfyldning("Alex", 50, destillering3, destillat8);

        Controller.fadPåfyldning(fad1, destillat1);
        Controller.fadPåfyldning(fad2, destillat2);
        Controller.fadPåfyldning(fad4, destillat3);
        Controller.fadPåfyldning(fad5, destillat4);
        Controller.fadPåfyldning(fad6, destillat5);
        Controller.fadPåfyldning(fad7, destillat6);
        Controller.fadPåfyldning(fad8, destillat7);
        Controller.fadPåfyldning(fad9, destillat8);

        Controller.setDestillatStartDato(destillat1, LocalDate.now().minusYears(4));
        Controller.setDestillatStartDato(destillat2, LocalDate.of(2020, 1, 1));
        Controller.setDestillatStartDato(destillat3, LocalDate.now().minusYears(3));
        Controller.setDestillatStartDato(destillat4, LocalDate.now().minusYears(5));
        Controller.setDestillatStartDato(destillat5, LocalDate.of(2019, 1, 1));
        Controller.setDestillatStartDato(destillat6, LocalDate.of(2021, 3, 5));

        contReol1.addFad(fad1, 1);
        contReol1.addFad(fad2, 2);
        contReol1.addFad(fad4, 3);

        contReol2.addFad(fad5, 1);
        contReol2.addFad(fad9, 2);

        ladeReol1.addFad(fad6, 1);
        ladeReol1.addFad(fad7, 2);
        ladeReol1.addFad(fad8, 3);

        WhiskyProdukt tørv = Controller.createWhiskyProdukt("TØRV");
        WhiskyProdukt muld = Controller.createWhiskyProdukt("MULD");


        FadTapning ft1 = Controller.createFadTapning("Chris", fad4, tørv);
        FadTapning ft2 = Controller.createFadTapning("Chris", fad5, tørv);

        FadTapning ft3 = Controller.createFadTapning("Maintz", fad1, muld);

        Controller.createWhiskyflasker(tørv);

    }


}
