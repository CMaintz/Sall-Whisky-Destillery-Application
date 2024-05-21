package gui;

import application.controller.Controller;
import application.controller.Storage;
import javafx.application.Application;
import storage.ListStorage;

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

    public static void initStorage() {
//        TODO opretter objekter af alle slags

    }


}
