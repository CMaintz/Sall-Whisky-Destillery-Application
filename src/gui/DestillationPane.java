package gui;

import application.controller.Controller;
import application.models.Destillat;
import application.models.Destillering;
import application.models.Fad;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import storage.Storage;

public class DestillationPane extends GridPane {
    private Controller controller;


    public DestillationPane() {
        controller = new Controller();



        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //Knapper

        Button opretDestillation = new Button("Opret destillering");

        Button tilføjKorn = new Button("Tilføj korn");


        //Listviews


        this.add(opretDestillation, 0, 1);
        this.add(tilføjKorn, 0, 2);








        //Action til knapper




        tilføjKorn.setOnAction(e -> {
            OpretKorn newWindow = new OpretKorn();
        });


    }

}
