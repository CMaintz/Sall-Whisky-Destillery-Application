package gui;

import application.controller.Controller;
import application.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

public class PåfyldFad extends Stage {
    private Fad fad;
    private GridPane textFieldsGrid;
    private List<TextField> textFieldsList = new ArrayList<>();
    private ListView<Destillering> lvwDestilleringer;
    private Button btnPåfyld = new Button("Påfyld Fad");
    private TextField txfMedarbejderNavn;
    private ComboBox<Lager> cbLagere;
    private ComboBox<Reol> cbReoler;
    private ComboBox<Hylde> cbHylder;

    public PåfyldFad(String title, Stage owner, Fad fad) {
        this.fad = fad;
        this.initOwner(owner);
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setMinHeight(200);
        this.setMinWidth(500);
        this.setResizable(false);

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPrefWidth(500);

        Label lblDestilleringer = new Label("Destilleringer");
        pane.add(lblDestilleringer, 0, 0);

        Label lblMedarbejderNavn = new Label("Medarbejder Navn");
        pane.add(lblMedarbejderNavn, 0, 6);

        lvwDestilleringer = new ListView<>();
        lvwDestilleringer.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        pane.add(lvwDestilleringer, 0, 1, 2, 5);

        ObservableList<Destillering> destilleringer = FXCollections.observableArrayList(Controller.getDestilleringer());
        lvwDestilleringer.setItems(destilleringer);

        lvwDestilleringer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateTextFields());

        txfMedarbejderNavn = new TextField();
        pane.add(txfMedarbejderNavn, 1, 6);
        txfMedarbejderNavn.setPrefWidth(185);

        textFieldsGrid = new GridPane();
        textFieldsGrid.setVgap(10);
        textFieldsGrid.setHgap(10);
        pane.add(textFieldsGrid, 0, 7, 2, 1);

        btnPåfyld.setOnAction(event -> PåfyldAction());
    }

    private void updateTextFields() {
        textFieldsGrid.getChildren().clear();
        textFieldsList.clear();

        int row = 0;
        for (Destillering selectedDestillering : lvwDestilleringer.getSelectionModel().getSelectedItems()) {
            TextField textField = new TextField();
            textField.setPromptText("Indtast Liter " + selectedDestillering.toString());

            textFieldsGrid.add(new Label(selectedDestillering.toString()), 0, row);
            textField.setPrefWidth(185);
            textFieldsGrid.add(textField, 1, row);

            textFieldsList.add(textField);
            row++;
        }

        cbLagere = new ComboBox<>();
        textFieldsGrid.add(new Label("Lager"), 0, row);
        textFieldsGrid.add(cbLagere, 1, row);
        cbLagere.getItems().setAll(Controller.getLagre());

        cbReoler = new ComboBox<>();
        textFieldsGrid.add(new Label("Reol"), 0, row + 1);
        textFieldsGrid.add(cbReoler, 1, row + 1);

        cbHylder = new ComboBox<>();
        textFieldsGrid.add(new Label("Hylde"), 0, row + 2);
        textFieldsGrid.add(cbHylder, 1, row + 2);

        cbLagere.setOnAction(event -> {
            Lager selectedLager = cbLagere.getSelectionModel().getSelectedItem();
            if (selectedLager != null) {
                cbReoler.getItems().setAll(selectedLager.getReolerMedLedigPlads());
            }
        });

        cbReoler.setOnAction(event -> {
            Reol selectedReol = cbReoler.getSelectionModel().getSelectedItem();
            if (selectedReol != null) {
                cbHylder.getItems().setAll(selectedReol.getHylderUdenFad());
            }
        });

        textFieldsGrid.add(btnPåfyld, 0, row + 3, 2, 1);
    }

    private void PåfyldAction() {
        String medarbejderNavn = txfMedarbejderNavn.getText().trim();
        if (medarbejderNavn.isEmpty()) {
            showAlert("Missing Input", "Please enter the employee name");
            return;
        }

        double volumeIAlt = 0;
        int index = 0;
        for (Destillering selectedDestillering : lvwDestilleringer.getSelectionModel().getSelectedItems()) {
            TextField textField = textFieldsList.get(index);
            try {
                double volume = Double.parseDouble(textField.getText());
                if (volume > 0) {
                    if (volume > selectedDestillering.getAntalLiter()) {
                        showAlert("Volume Exceeds Available", "The volume entered exceeds the available volume in the selected destillering.");
                        return;
                    }
                    volumeIAlt += volume;
                } else {
                    showAlert("Invalid Volume", "Volume must be greater than 0");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid number for the volume");
                return;
            }
            index++;
        }

        if (volumeIAlt > fad.getLiterKapacitet()) {
            showAlert("Volume Exceeds Capacity", "The total volume of the distillate exceeds the barrel's capacity.");
            return;
        }

        Destillat destillat = new Destillat();
        index = 0;
        for (Destillering selectedDestillering : lvwDestilleringer.getSelectionModel().getSelectedItems()) {
            TextField textField = textFieldsList.get(index);
            double volume = Double.parseDouble(textField.getText());
            destillat.createPåfyldning(medarbejderNavn, volume, selectedDestillering);
            index++;
        }

        Lager lager = cbLagere.getSelectionModel().getSelectedItem();
        if (lager == null) {
            showAlert("Invalid Lager", "Vælg et Lager");
            return;
        }
        Reol reol = cbReoler.getSelectionModel().getSelectedItem();
        if (reol == null) {
            showAlert("Invalid Reol", "Vælg en reol");
            return;
        }
        Hylde hylde = cbHylder.getSelectionModel().getSelectedItem();
        if (hylde == null) {
            showAlert("Invalid Hylde", "Vælg en hylde");
            return;
        }

        fad.addDestillat(destillat);
        hylde.placerFad(fad);
        this.close();
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
