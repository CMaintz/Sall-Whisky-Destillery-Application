package gui;

import application.controller.Controller;
import application.models.Destillering;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class DestilleringPane extends GridPane {
    private Controller controller;
    private ListView<Destillering> destilleringListView;
    private Button tilføjKorn = new Button();
    private Button opretDestillering = new Button();

    public DestilleringPane() {
        GridPane pane = new GridPane();
        this.add(pane, 0, 0);
        pane.setGridLinesVisible(false);
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setStyle("-fx-border-color: black");

        Label lblDestilleringer = new Label("Destilleringer");
        pane.add(lblDestilleringer, 1, 0);

        destilleringListView = new ListView<>();
        pane.add(destilleringListView, 1, 1, 2, 5);
        destilleringListView.setPrefWidth(250);
        destilleringListView.setPrefHeight(200);
        ObservableList<Destillering> destilleringer = FXCollections.observableArrayList(Controller.getDestilleringer());
        destilleringListView.setItems(destilleringer);

        Label lblDestilleringInfo = new Label("Destillering info");
        pane.add(lblDestilleringInfo, 3, 0);

        TextArea taDestilleringInfo = new TextArea();
        pane.add(taDestilleringInfo, 3, 1, 3, 5);
        taDestilleringInfo.setEditable(false);
        taDestilleringInfo.setPrefWidth(250);
        taDestilleringInfo.setPrefHeight(200);

        opretDestillering.setText("Opret destillering");
        pane.add(opretDestillering, 0, 0);

        tilføjKorn.setText("Tilføj korn");
        pane.add(tilføjKorn, 0, 1);

        //Action til knapper -----------------------------------------------------------------------------------------------------------
        opretDestillering.setOnAction(e -> {
            OpretDestillering newWindow = new OpretDestillering();
            newWindow.setOnHidden(event -> {
                ObservableList<Destillering> updatedDestilleringList = FXCollections.observableArrayList(Controller.getDestilleringer());
                destilleringListView.setItems(updatedDestilleringList);
            });
        });

        tilføjKorn.setOnAction(e -> {
            OpretKorn newWindow = new OpretKorn();
        });

        destilleringListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Destillering selectedDestillering = newValue;
                String destilleringInfo = String.format(
                        "MaltBatch: %s\nKorn: %s\nMedarbejder: %s\nMængde Væske: %.2f\nAlkoholprocent: %.2f\nRyge Materiale: %s\nKommentar: %s",
                        selectedDestillering.getMaltBatch(),
                        selectedDestillering.getKornSort(),
                        selectedDestillering.getMedarbejderNavn(),
                        selectedDestillering.getAntalLiter(),
                        selectedDestillering.getAlkoholProcent(),
                        selectedDestillering.getRygemateriale(),
                        selectedDestillering.getKommentar()
                );
                taDestilleringInfo.setText(destilleringInfo);
            } else {
                taDestilleringInfo.clear();
            }
        });
    }
}
