package gui;

import application.controller.Controller;
import application.models.Fad;
import application.models.Hylde;
import application.models.Lager;
import application.models.Reol;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LagerstyringPane extends GridPane {
    private ListView<Lager> lvwLagre;
    private ListView<Reol> lvwReoler;
    private ListView<Hylde> lvwHylder;
    private TextArea taInfo = new TextArea();

    public LagerstyringPane() {
        GridPane pane = new GridPane();
        this.add(pane, 0, 0);
        pane.setGridLinesVisible(false);
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setStyle("-fx-border-color: black");

        // Labels
        Label lblLagre = new Label("Lagre");
        pane.add(lblLagre, 0, 1);

        Label lblReoler = new Label("Reoler");
        pane.add(lblReoler, 1, 1);

        Label lblHylder = new Label("Hylder");
        pane.add(lblHylder, 2, 1);

        // ListViews
        lvwLagre = new ListView<>();
        pane.add(lvwLagre, 0, 2);
        lvwLagre.setPrefWidth(180);
        lvwLagre.setPrefHeight(350);

        lvwReoler = new ListView<>();
        pane.add(lvwReoler, 1, 2);
        lvwReoler.setPrefWidth(180);
        lvwReoler.setPrefHeight(350);

        lvwHylder = new ListView<>();
        pane.add(lvwHylder, 2, 2);
        lvwHylder.setPrefWidth(180);
        lvwHylder.setPrefHeight(350);

        ObservableList<Lager> lagre = FXCollections.observableArrayList(Controller.getLagre());
        lvwLagre.setItems(lagre);

        lvwLagre.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateReoler(newValue));
        lvwReoler.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateHylder(newValue));
        lvwHylder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateFadInfo(newValue));

        Button btnOpretLager = new Button();
        Button btnFlytFad = new Button();
        Button btnOpretReol = new Button();

        btnOpretLager.setText("Opret Lager");
        pane.add(btnOpretLager, 0, 0);

        btnFlytFad.setText("Flyt fad");
        pane.add(btnFlytFad, 1, 0);

        btnOpretReol.setText("Opret Reol");
        pane.add(btnOpretReol, 2, 0);

        pane.add(taInfo, 3, 1, 4, 5);
        taInfo.setPrefWidth(250);
        taInfo.setPrefHeight(200);
        taInfo.setEditable(false);

        updateListViews();

        btnOpretLager.setOnAction(event -> opretLagerAction());
        btnFlytFad.setOnAction(event -> flytFadAction());
        btnOpretReol.setOnAction(event -> opretReolAction());
    }

    private void opretLagerAction() {
        OpretLager opretLager = new OpretLager();
        opretLager.showAndWait();
        updateListViews();
    }

    private void opretReolAction() {
        Lager lager = lvwLagre.getSelectionModel().getSelectedItem();

        if (lager != null) {
            OpretReol opretReol = new OpretReol("Opret Reol", new Stage(), lager);
            opretReol.showAndWait();
            updateListViews();
        } else {
            showAlert("Invalid Input", "Vælg et lager");
        }
    }

    private void updateFadInfo(Hylde selectedHylde) {
        if (selectedHylde != null && selectedHylde.getFad() != null) {
            Fad fad = selectedHylde.getFad();
            String fadInfo = String.format(
                    "Fad Nr: %s\nLiter Kapacitet: %d\nAlder (måneder): %d\nType: %s\nDestillat: %s",
                    fad.getFadNr(),
                    fad.getLiterKapacitet(),
                    fad.getAlderMåneder(),
                    fad.getType(),
                    fad.getDestillat() != null ? fad.getDestillat().toString() : "N/A"
            );
            taInfo.setText(fadInfo);
        } else {
            taInfo.clear();
        }
    }



    private void flytFadAction() {
        Hylde hylde = lvwHylder.getSelectionModel().getSelectedItem();
        if (hylde != null) {
            Fad fad = hylde.getFad();
            if (fad != null) {
                FlytFadWindow flytFadWindow = new FlytFadWindow(fad, hylde);
                flytFadWindow.showAndWait();
            } else {
                showAlert("Invalid Input", "Vælg en hylde med et fad på");
                return;
            }
        } else {
            showAlert("Invalid Input", "Vælg en hylde");
            return;
        }
    }

    private void updateReoler(Lager selectedLager) {
        if (selectedLager != null) {
            ObservableList<Reol> reoler = FXCollections.observableArrayList(selectedLager.getReoler());
            lvwReoler.setItems(reoler);
            lvwHylder.getItems().clear();
        }
    }

    private void updateHylder(Reol selectedReol) {
        if (selectedReol != null) {
            ObservableList<Hylde> hylder = FXCollections.observableArrayList(selectedReol.getHylder());
            lvwHylder.setItems(hylder);
        }
    }

    private void updateListViews() {
        lvwLagre.getItems().setAll(Controller.getLagre());
        updateReoler(lvwLagre.getSelectionModel().getSelectedItem());
        updateHylder(lvwReoler.getSelectionModel().getSelectedItem());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
