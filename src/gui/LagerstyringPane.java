package gui;

import application.controller.Controller;
import application.models.Fad;
import application.models.Lager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import storage.Storage;

public class LagerstyringPane extends GridPane {
    private Controller controller;
    private ListView<Lager> lagerListView;
    private ListView<Fad> fadListView;

    public LagerstyringPane() {
        controller = new Controller();
        controller.createSomeObjects();

        lagerListView = new ListView<>();
        fadListView = new ListView<Fad>();

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lagerLabel = new Label("Lagre:");
        Label fadLabel = new Label("Fyldte fade:");



        //Knapper
        Button opretLager = new Button("Opret lager");
        Button visLagerIndhold = new Button("Vis lagerindhold");
        Button tilføjTilLager = new Button("Tilføj til lager");


        //Listviews
        ObservableList<Lager> lagerList = FXCollections.observableArrayList(Storage.getLager());
        lagerListView.setItems(lagerList);

        Lager lager = lagerListView.getSelectionModel().getSelectedItem();
        if  (lager != null) {
            ObservableList<Fad> fadList = FXCollections.observableArrayList(lager.getFadeMedDestillat());
            fadListView.setItems(fadList);
        }

        this.add(opretLager, 0, 0);
        this.add(lagerListView, 0, 3);
        this.add(fadListView, 2, 3);
        this.add(lagerLabel, 0, 2);
        this.add(fadLabel, 2, 2);
        this.add(visLagerIndhold, 3, 3);
        this.add(tilføjTilLager, 3, 4);






        //Action til knapper
        opretLager.setOnAction(e -> {
             OpretLager newWindow = new OpretLager();
                newWindow.setOnHidden(event -> {
                 ObservableList<Lager> lagerList1 = FXCollections.observableArrayList(Storage.getLager());
                    lagerListView.setItems(lagerList1);
                });
        });

        visLagerIndhold.setOnAction(e -> {
            Lager selectedLager = lagerListView.getSelectionModel().getSelectedItem();

            if(selectedLager != null) {
                Stage stage = new Stage();
                stage.setTitle("Lagerindhold");

                ListView<Fad> fadListView = new ListView<>();
                fadListView.getItems().addAll(selectedLager.getFadeMedDestillat());

                Scene scene = new Scene(fadListView, 300, 300);
                stage.setScene(scene);

                stage.show();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fejl");
                alert.setHeaderText(null);
                alert.setContentText("Vælg et lager først");
                alert.showAndWait();
            }
        });



    }

}
