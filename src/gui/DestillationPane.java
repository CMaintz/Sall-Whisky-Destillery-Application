package gui;

import application.controller.Controller;
import application.models.Destillat;
import application.models.Destillering;
import application.models.Fad;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import storage.Storage;

public class DestillationPane extends GridPane {
    private ListView<Destillering> destilleringListView;
    private Controller controller;


    public DestillationPane() {
        controller = new Controller();



        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        destilleringListView = new ListView<Destillering>();

        //Knapper
        Button opretDestillering = new Button("Opret destillering");
        Button tilføjKorn = new Button("Tilføj korn");


        //Listviews
        ObservableList<Destillering> destilleringList = FXCollections.observableArrayList(Controller.getDestilleringer());
        destilleringListView.setItems(destilleringList);


        this.add(opretDestillering, 0, 1);
        this.add(tilføjKorn, 0, 2);
        this.add(destilleringListView, 1, 3);








        //Action til knapper
        opretDestillering.setOnAction(e -> {
            OpretDestillation newWindow = new OpretDestillation();
            newWindow.show();
        });

        destilleringList.addListener((InvalidationListener) observable -> {
            destilleringListView.setItems(FXCollections.observableArrayList(Controller.getDestilleringer()));
        });



        tilføjKorn.setOnAction(e -> {
            OpretKorn newWindow = new OpretKorn();
        });


    }

}
