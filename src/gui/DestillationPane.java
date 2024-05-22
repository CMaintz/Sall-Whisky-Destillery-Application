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

public class DestillationPane extends GridPane {
    private Controller controller;
    private ListView<Fad> fadListView;
    private ListView<Destillering> destilleringListView;

    public DestillationPane() {
//        controller = new Controller();
        fadListView = new ListView<>();
        destilleringListView = new ListView<>();

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //Knapper
        Button opretFad = new Button("Opret fad");

        Button opretDestillation = new Button("Opret destillation");

        Button tilføjKorn = new Button("Tilføj korn");

        Button påfyldFad = new Button("Påfyld fad");
        påfyldFad.setPrefSize(100,150);

        //Listviews
        ObservableList<Fad> fadList = FXCollections.observableArrayList(Controller.getFade());
        fadListView.setItems(fadList);

        ObservableList<Destillering> destilleringList = FXCollections.observableArrayList(Controller.getDestilleringer());
        destilleringListView.setItems(destilleringList);

        this.add(opretFad, 0, 0);
        this.add(opretDestillation, 0, 1);
        this.add(tilføjKorn, 0, 2);
        this.add(fadListView, 0, 3);
        this.add(destilleringListView, 2, 3);
        this.add(påfyldFad, 3,3);






        //Action til knapper
        opretFad.setOnAction(e -> {
            OpretFad newWindow = new OpretFad();
            newWindow.setOnHidden(event -> {
                ObservableList<Fad> updatedFadList = FXCollections.observableArrayList(Controller.getFade());
                fadListView.setItems(updatedFadList);
            });
        });

        opretDestillation.setOnAction(e -> {
            OpretDestillation newWindow = new OpretDestillation();
            newWindow.setOnHidden(event -> {
                ObservableList<Destillering> updatedDestilleringList = FXCollections.observableArrayList(Controller.getDestilleringer());
                destilleringListView.setItems(updatedDestilleringList);
            });
        });

        tilføjKorn.setOnAction(e -> {
            OpretKorn newWindow = new OpretKorn();
        });

//        påfyldFad.setOnAction((e -> {
//            Fad selectedFad = fadListView.getSelectionModel().getSelectedItem();
//            Destillering selectedDestillering = destilleringListView.getSelectionModel().getSelectedItem();


//            } else {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Fejl");
//                alert.setHeaderText(null);
//                alert.setContentText("Vælg venligst et fad og en destillering");
//                alert.showAndWait();
//            }
//        }));

    }

}
