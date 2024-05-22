package gui;

import application.controller.Controller;
import application.models.Fad;
import application.models.Hylde;
import application.models.Lager;
import application.models.Reol;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FlytFadWindow extends Stage {
    private ComboBox<Lager> cbLagre = new ComboBox<>();
    private ComboBox<Reol> cbReoler = new ComboBox<>();
    private ComboBox<Hylde> cbHylder = new ComboBox<>();
    Fad fad;
    Hylde hyldefra;
    public FlytFadWindow(Fad fad, Hylde hyldeFra) {
        this.fad = fad;
        this.hyldefra = hyldeFra;
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setHgap(20);
        pane.setVgap(10);
        Scene scene = new Scene(pane, 300, 300);
        this.setScene(scene);
        this.setTitle("Opret Lager");

        // Labels
        Label lblLager = new Label("Lager:");
        pane.add(lblLager, 0, 0);

        Label lblReol = new Label("Reol:");
        pane.add(lblReol, 0, 1);

        Label lblHylde = new Label("Hylde:");
        pane.add(lblHylde, 0, 2);

        // ComboBoxes
        pane.add(cbLagre, 1, 0);

        pane.add(cbReoler, 1, 1);

        pane.add(cbHylder, 1, 2);

        cbLagre.setItems(FXCollections.observableArrayList(Controller.getLagre()));

        cbLagre.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cbReoler.setItems(FXCollections.observableArrayList(newValue.getReolerMedLedigPlads()));
            }
        });

        cbReoler.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cbHylder.setItems(FXCollections.observableArrayList(newValue.getHylderUdenFad()));
            }
        });

        Button btnFlytFad = new Button();

        btnFlytFad.setText("Flyt fad");
        pane.add(btnFlytFad, 0, 4);

        btnFlytFad.setOnAction(event -> flytFadAction());

    }

    private void flytFadAction() {
        Lager lager = cbLagre.getSelectionModel().getSelectedItem();
        if (lager != null) {
            Reol reol = cbReoler.getSelectionModel().getSelectedItem();
            if (reol != null) {
                Hylde hylde = cbHylder.getSelectionModel().getSelectedItem();
                if (hylde != null) {
                    Controller.flytFad(fad, hyldefra, hylde);
                    this.hide();
                } else {
                    showAlert("Invalid Input", "Vælg en hylde");
                    return;
                }
            } else {
                showAlert("Invalid Input", "Vælg en Reol");
                return;
            }
        } else {
            showAlert("Invalid Input", "Vælg et lager");
            return;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
