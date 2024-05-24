package gui;

import application.controller.Controller;
import application.models.Destillat;
import application.models.Fad;
import application.models.WhiskyProdukt;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class WhiskyWindow extends Stage {

    private int literVand;
    private ListView<Fad> lvwFærdigeDestillater;
    private ListView<Destillat> lvwValgteDestillater;
    private TextField txfProduktNavn, txfMedarbejder, txfVand, txfAlk;
    private TextArea txaDestillatInfo;
    private Button btnAddDestillat, btnFjernDestillat, btnTilføjVand, btnOpret;
    private Label lblLiter, lblVand, lblAlk;


    public WhiskyWindow(String title) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.setTitle(title);
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10));
        pane.setHgap(5);
        pane.setVgap(10);
        pane.setPrefWidth(1000);

        Scene scene = new Scene(pane);
        this.setScene(scene);

        this.initContent(pane);
    }

    private void initContent(GridPane pane) {
        GridPane currentChoicesPane = new GridPane();
        currentChoicesPane.setPadding(new Insets(10));
        currentChoicesPane.setHgap(20);
        currentChoicesPane.setVgap(10);
        pane.add(currentChoicesPane, 0, 0);

        VBox vb = new VBox();
        currentChoicesPane.add(vb, 0, 1);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(10);
        vb.setPadding(new Insets(5));

        Label lblName = new Label("Produkt navn:");
        currentChoicesPane.add(lblName, 0, 0);
        GridPane.setHalignment(lblName, HPos.CENTER);

        txfProduktNavn = new TextField();
        txfProduktNavn.setPrefWidth(100);
        vb.getChildren().add(txfProduktNavn);

        lblLiter = new Label("Liter: 0");
        vb.getChildren().add(lblLiter);

        lblAlk = new Label("0 % Vol.");
        vb.getChildren().add(lblAlk);

        lblVand = new Label("Vand: 0L");
        vb.getChildren().add(lblVand);

        HBox hb = new HBox();
        vb.getChildren().add(hb);
        hb.setSpacing(5);

        btnTilføjVand = new Button("Tilføj Vand");
        btnTilføjVand.setOnAction(event -> tilføjVand());
        hb.getChildren().add(btnTilføjVand);

        txfVand = new TextField();
        txfVand.setPrefWidth(75);
        hb.getChildren().add(txfVand);

        Label lblMedarbejder = new Label("Medarbejdernavn:");
        vb.getChildren().add(lblMedarbejder);

        txfMedarbejder = new TextField();
        vb.getChildren().add(txfMedarbejder);

        GridPane lvwPane1 = new GridPane();
        lvwPane1.setPadding(new Insets(10));
        lvwPane1.setHgap(20);
        lvwPane1.setVgap(10);
        lvwPane1.setPrefWidth(240);
        pane.add(lvwPane1, 1, 0);

        Label lblValgte = new Label("Valgte destillater");
        lvwPane1.add(lblValgte, 0, 0);

        lvwValgteDestillater = new ListView<>();
        lvwPane1.add(lvwValgteDestillater, 0, 1);
        lvwValgteDestillater.setPrefWidth(200);
        lvwValgteDestillater.setPrefHeight(240);

        btnFjernDestillat = new Button("Fjern Destillat");
        btnFjernDestillat.setOnAction(event -> removeAction());
        pane.add(btnFjernDestillat, 1, 1);
        GridPane.setHalignment(btnFjernDestillat, HPos.CENTER);

        GridPane lvwPane2 = new GridPane();
        lvwPane2.setPadding(new Insets(10));
        lvwPane2.setHgap(20);
        lvwPane2.setVgap(10);
        lvwPane2.setPrefWidth(240);
        pane.add(lvwPane2, 2, 0);

        Label lblFærdigeDestillater = new Label("Fade klar til tapning");
        lvwPane2.add(lblFærdigeDestillater, 0, 0);

        lvwFærdigeDestillater = new ListView<>();
        lvwPane2.add(lvwFærdigeDestillater, 0, 1);
        lvwFærdigeDestillater.setPrefWidth(200);
        lvwFærdigeDestillater.setPrefHeight(240);
        lvwFærdigeDestillater.getItems().setAll(Controller.getFadeMedFærdigDestillat());

        ChangeListener<Fad> fadListener = (ov, oldFad, newFad) -> this.opdaterInfo();
        lvwFærdigeDestillater.getSelectionModel().selectedItemProperty().addListener(fadListener);

        btnAddDestillat = new Button("Tilføj Destillat");
        btnAddDestillat.setOnAction(event -> addAction());
        pane.add(btnAddDestillat, 2, 1);
        GridPane.setHalignment(btnAddDestillat, HPos.CENTER);

        GridPane infoPane = new GridPane();
        infoPane.setPadding(new Insets(10));
        infoPane.setHgap(20);
        infoPane.setVgap(10);
        infoPane.setPrefWidth(300);
        pane.add(infoPane, 3, 0);

        Label lblDestillatInfo = new Label("Destillat info");
        infoPane.add(lblDestillatInfo, 0, 0);

        txaDestillatInfo = new TextArea();
        infoPane.add(txaDestillatInfo, 0, 1);
        txaDestillatInfo.setEditable(false);
        txaDestillatInfo.setPrefWidth(300);
        txaDestillatInfo.setPrefHeight(240);

        Button btnCancel = new Button("Fortryd");
        pane.add(btnCancel, 3, 1);
        GridPane.setHalignment(btnCancel, HPos.RIGHT);
        btnCancel.setCancelButton(true);
        btnCancel.setOnAction(event -> this.cancelAction());

        btnOpret = new Button("Opret");
        pane.add(btnOpret, 3, 1);
        GridPane.setHalignment(btnOpret, HPos.LEFT);
        btnOpret.setOnAction(event -> this.opretAction());

    }

    private void tilføjVand() {
        try {
            literVand += Integer.parseInt(txfVand.getText().trim());
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fejl");
            alert.setContentText("Fejl: Liter vand godtager kun heltal");
            alert.showAndWait();
        }
        opdaterCurrent();
    }

    private void addAction() {
        Fad fad = lvwFærdigeDestillater.getSelectionModel().getSelectedItem();
        if (fad != null) {
            Destillat destillat = fad.getDestillat();
            lvwValgteDestillater.getItems().add(destillat);
            lvwFærdigeDestillater.getItems().remove(fad);
            opdaterCurrent();
        }

    }

    private void removeAction() {
        Destillat destillat = lvwValgteDestillater.getSelectionModel().getSelectedItem();
        if (destillat != null) {
            Fad fad = destillat.getFad();
            lvwValgteDestillater.getItems().remove(destillat);
            lvwFærdigeDestillater.getItems().add(fad);
            opdaterCurrent();
        }
    }

    private void opdaterCurrent() {
        List<Destillat> valgteDestillater = new ArrayList<>(lvwValgteDestillater.getItems());
        lblVand.setText("Vand: " + literVand + "L");
        lblLiter.setText("Liter: " + Controller.udregnTotalLiter(valgteDestillater, literVand));
        if (!valgteDestillater.isEmpty()) {
            DecimalFormat numberFormatter = new DecimalFormat("#.00");
            double alkoholprocent = Controller.udregnAlkoholprocent(valgteDestillater, literVand);
            lblAlk.setText(numberFormatter.format(alkoholprocent) + "% Vol.");
        } else {
            lblAlk.setText("0% Vol.");
        }
    }

    private void opdaterInfo() {
        Fad fad = lvwFærdigeDestillater.getSelectionModel().getSelectedItem();
        if (fad != null) {
            txaDestillatInfo.setText(fad.getDestillat().getDetaljer());
        }
    }


    private void cancelAction() {
        this.close();
    }

    private void opretAction() {
        String produktNavn = txfProduktNavn.getText().trim();
        String medarbejder = txfMedarbejder.getText().trim();
        ArrayList<Destillat> valgteDestillater = new ArrayList<>(lvwValgteDestillater.getItems());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText("Fejl i oprettelse");
        if (produktNavn.isEmpty()) {
            alert.setContentText("Udfyld produktnavn");
            alert.showAndWait();
        } else if (medarbejder.isEmpty()) {
            alert.setContentText("Udfyld medarbejdernavn");
            alert.showAndWait();
        } else if (valgteDestillater.isEmpty()) {
            alert.setContentText("Du har ikke tappet fade");
            alert.showAndWait();
        } else {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Opret produkt");
            confirmation.setHeaderText("Bekræft produkt oprettelse");
            confirmation.setContentText("OBS: Når bekræftet, kan du ikke fortryde.");
            confirmation.showAndWait();
            if (confirmation.getResult() == ButtonType.OK) {
                WhiskyProdukt whisky = Controller.createWhiskyProdukt(produktNavn);
                for (Destillat destillat : valgteDestillater) {
                    Controller.createFadTapning(medarbejder, destillat.getFad(), whisky);
                }
            }
            this.close();
        }
    }

}
