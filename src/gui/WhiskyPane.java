package gui;

import application.controller.Controller;
import application.models.WhiskyFlaske;
import application.models.WhiskyProdukt;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class WhiskyPane extends GridPane {
    private ListView<WhiskyProdukt> lvwWhiskyProdukter;
    private ListView<WhiskyFlaske> lvwFlasker;
    private TextArea txaWhiskyInfo, txaProduktHistorie;
    private Label lblWhiskyInfo, lblFlasker;
    private Button btnFyldFlasker;
    private static final int PREF_WIDTH = 100;
    private static final int PREF_HEIGHT = 240;

    public WhiskyPane() {
        this.setPadding(new Insets(5));
        this.setHgap(10);
        this.setVgap(10);

        GridPane pane = new GridPane();
        this.add(pane, 0, 0);
        pane.setPadding(new Insets(5));
        pane.setHgap(20);
        pane.setVgap(10);

        Label lblWhisky = new Label("Whisky");
        pane.add(lblWhisky, 0, 0);

        lvwWhiskyProdukter = new ListView<>();
        pane.add(lvwWhiskyProdukter, 0, 1);
        lvwWhiskyProdukter.setPrefWidth(PREF_WIDTH + 40);
        lvwWhiskyProdukter.setPrefHeight(PREF_HEIGHT);
        lvwWhiskyProdukter.getItems().setAll(Controller.getWhiskyProdukter());
        lvwWhiskyProdukter.getSelectionModel().selectFirst();


        ChangeListener<WhiskyProdukt> whiskyListener = (ov, oldWhisky, newWhisky) -> selectedProductChanged();
        lvwWhiskyProdukter.getSelectionModel().selectedItemProperty().addListener(whiskyListener);

        lblWhiskyInfo = new Label(lvwWhiskyProdukter.getItems().get(0).getNavn() + " detaljer");
        pane.add(lblWhiskyInfo, 1, 0);

        txaWhiskyInfo = new TextArea();
        txaWhiskyInfo.setEditable(false);
        txaWhiskyInfo.setPrefWidth(PREF_WIDTH * 2);
        txaWhiskyInfo.setPrefHeight(PREF_HEIGHT);
        pane.add(txaWhiskyInfo, 1, 1);
        txaWhiskyInfo.setText(lvwWhiskyProdukter.getItems().get(0).getDetaljer());

        lblFlasker = new Label(lvwWhiskyProdukter.getItems().get(0).getNavn() + " flasker");
        pane.add(lblFlasker, 2, 0);

        lvwFlasker = new ListView<>();
        lvwFlasker.setPrefWidth(PREF_WIDTH);
        lvwFlasker.setPrefHeight(PREF_HEIGHT);
        pane.add(lvwFlasker, 2, 1);

        ChangeListener<WhiskyFlaske> flaskeListener = (ov, oldFlaske, newFlaske) -> this.selectedFlaskeChanged();
        lvwFlasker.getSelectionModel().selectedItemProperty().addListener(flaskeListener);

        Label lblHistorie = new Label("Produkthistorie");
        pane.add(lblHistorie, 3, 0);

        txaProduktHistorie = new TextArea();
        txaProduktHistorie.setEditable(false);
        txaProduktHistorie.setPrefWidth(PREF_WIDTH * 2);
        txaProduktHistorie.setPrefHeight(PREF_HEIGHT);
        pane.add(txaProduktHistorie, 3, 1);

        Button btnNewWhisky = new Button("Opret Whisky");
        pane.add(btnNewWhisky, 0, 2);
        btnNewWhisky.setOnAction(event -> createWhiskyAction());

        btnFyldFlasker = new Button("Fyld på flasker");
        pane.add(btnFyldFlasker, 0, 3);
        btnFyldFlasker.setOnAction(event -> createFlaskeAction());
        btnFyldFlasker.setDisable(true);
    }


    private void createWhiskyAction() {
        int index = lvwWhiskyProdukter.getSelectionModel().getSelectedIndex();
        WhiskyWindow dia = new WhiskyWindow("Opret Produkt");
        dia.showAndWait();
        lvwWhiskyProdukter.getItems().setAll(Controller.getWhiskyProdukter());
        lvwWhiskyProdukter.getSelectionModel().select(index);
    }

    private void createFlaskeAction() {
        WhiskyProdukt whisky = lvwWhiskyProdukter.getSelectionModel().getSelectedItem();
        if (whisky != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setHeaderText("Flaskepåfyldning");
            confirmation.setTitle("Bekræft flaskning");
            confirmation.setContentText("OBS: Når bekræftet, kan du ikke fortryde. \nFlaskning opretter samme antal flasker som produktets liter");
            confirmation.showAndWait();
            if (confirmation.getResult() == ButtonType.OK) {
                Controller.createWhiskyflasker(whisky);
                lvwFlasker.getItems().setAll(whisky.getFyldteFlasker());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Mangler information");
            alert.setTitle("Intet produkt valgt");
            alert.setContentText("Venligst vælg et gyldigt produkt");
            alert.showAndWait();
        }
    }

    public void selectedProductChanged() {
        WhiskyProdukt whisky = lvwWhiskyProdukter.getSelectionModel().getSelectedItem();
        if (whisky != null) {
            txaWhiskyInfo.setText(whisky.getDetaljer());
            lblWhiskyInfo.setText(whisky.getNavn() + " detaljer");
            lblFlasker.setText(whisky.getNavn() + " flasker");
            if (whisky.getFyldteFlasker().size() > 0) {
                lvwFlasker.getItems().setAll(whisky.getFyldteFlasker());
                btnFyldFlasker.setDisable(true);
                txaProduktHistorie.setText(lvwFlasker.getItems().get(0).getProduktHistorie());
            } else {
                lvwFlasker.getItems().clear();
                txaProduktHistorie.clear();
                btnFyldFlasker.setDisable(false);
            }
        }
    }


    private void selectedFlaskeChanged() {
        WhiskyFlaske whiskyFlaske = lvwFlasker.getSelectionModel().getSelectedItem();
        if (whiskyFlaske != null) {
            txaProduktHistorie.setText(whiskyFlaske.getProduktHistorie());
        }
    }


}
