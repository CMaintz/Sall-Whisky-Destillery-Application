package gui;

import application.controller.Controller;
import application.models.Fad;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FadVindue extends GridPane {

    private ListView<Fad> lvwTommeFade;
    private ListView<Fad> lvwFyldteFade;
    private Label lblError;
    private Button btnRegistrerNytFad = new Button();
    private Button btnVisHistorik = new Button();
    private Button btnPåfyldFad = new Button();
    private Button btnOmhæld = new Button();
    private Button btnRemoveFad = new Button();
    private OpretFad opretFadWindow;
    private PåfyldFad påfyldFad;


    public FadVindue() {
        GridPane pane = new GridPane();
        this.add(pane, 0, 0);
        pane.setGridLinesVisible(false);
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setStyle("-fx-border-color: black");

        Label tommeFade = new Label("Tomme fade");
        pane.add(tommeFade, 1, 0);

        lvwTommeFade = new ListView<>();
        pane.add(lvwTommeFade, 1, 1, 2, 5);
        lvwTommeFade.setPrefWidth(250);
        lvwTommeFade.setPrefHeight(200);
        lvwTommeFade.getItems().setAll(Controller.getTommeFade());

        Label lblFyldteFade = new Label("Fyldte fade");
        pane.add(lblFyldteFade, 3, 0);

        lvwFyldteFade = new ListView<>();
        pane.add(lvwFyldteFade, 3, 1, 3, 5);
        lvwFyldteFade.setPrefWidth(250);
        lvwFyldteFade.setPrefHeight(200);
        lvwFyldteFade.getItems().setAll(Controller.getFyldtefade());

        lblError = new Label();
        pane.add(lblError, 1, 8);
        lblError.setStyle("-fx-text-fill: red");

        btnRegistrerNytFad.setText("Registrer Nyt Fad");
        pane.add(btnRegistrerNytFad, 0, 1);

        btnRemoveFad.setText("Slet Fad");
        pane.add(btnRemoveFad, 0, 2);

        btnVisHistorik.setText("Vis Historik");
        pane.add(btnVisHistorik, 0, 3);

        btnPåfyldFad.setText("Påfyld Fad");
        pane.add(btnPåfyldFad, 1, 6);

        btnOmhæld.setText("Omhæld");
        pane.add(btnOmhæld, 3, 6);

        btnRegistrerNytFad.setOnAction(event -> registrerNytFadAction());
        btnRemoveFad.setOnAction(event -> removeFadAction());
        btnVisHistorik.setOnAction(event -> visHistorikAction());
        btnPåfyldFad.setOnAction(event -> påfyldFadAction());
        btnOmhæld.setOnAction(event -> omhældAction());
    }

    private void registrerNytFadAction() {
        opretFadWindow = new OpretFad("Opret Fad", new Stage());
        opretFadWindow.showAndWait();
        updateListViews();
    }

    private void removeFadAction() {
        Fad fad = lvwTommeFade.getSelectionModel().getSelectedItem();

        if (fad != null) {
            Controller.removeFad(fad);
            updateListViews();
        } else {
            lblError.setText("Vælg et tomt fad at slette");
        }
    }

    private void visHistorikAction() {
        lblError.setText("");
        Fad selectedFad = lvwTommeFade.getSelectionModel().getSelectedItem();

        if (selectedFad == null) {
            selectedFad = lvwFyldteFade.getSelectionModel().getSelectedItem();
            if (selectedFad == null) {
                lblError.setText("Vælg et fad");
            }
        }
        if (lblError.getText().isEmpty()) {
            VisHistorik visHistorik = new VisHistorik("Vis Fadhistorik", new Stage(), selectedFad);
            visHistorik.showAndWait();
            lblError.setText("");
        }
    }

    private void påfyldFadAction() {
        Fad selectedFad = lvwTommeFade.getSelectionModel().getSelectedItem();

        if (selectedFad == null) {
            lblError.setText("Vælg tomt fad");
        } else {
            påfyldFad = new PåfyldFad("Påfyld Fad", new Stage(), selectedFad);
            påfyldFad.showAndWait();
            updateListViews();
        }
    }

    private void omhældAction() {
        Fad fadTil = lvwTommeFade.getSelectionModel().getSelectedItem();
        Fad fadFra = lvwFyldteFade.getSelectionModel().getSelectedItem();

        if (fadTil == null) {
            lblError.setText("Vælg tomt fad");
        } else if (fadFra == null) {
            lblError.setText("Vælg fyldt fad");
        } else {
            Controller.omhældningAfDestillat(fadFra, fadTil);
        }
        updateListViews();
    }

    private void updateListViews() {
        lvwTommeFade.getItems().setAll(Controller.getTommeFade());
        lvwFyldteFade.getItems().setAll(Controller.getFyldtefade());
    }
}
