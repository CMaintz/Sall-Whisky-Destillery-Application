package gui;

import application.controller.Controller;
import application.models.FadHistorik;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OpretFad extends Stage {

    Button opretFad = new Button("Opret fad");
    public OpretFad() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setHgap(20);
        pane.setVgap(10);


        //Labels og textfields
        Label lblFadnr = new Label("Fadnummer");
        pane.add(lblFadnr, 0, 0);
        Label lblStørrelse = new Label("Størrelse");
        pane.add(lblStørrelse, 0, 2);
        Label lblAlder = new Label("Alder");
        pane.add(lblAlder, 0, 4);
        Label lblTidligereIndhold = new Label("Tidligere indhold");
        pane.add(lblTidligereIndhold, 0, 6);
        Label lblLand = new Label("Land");
        pane.add(lblLand, 0, 8);
        Label lblFraÅr = new Label("Fra år");
        pane.add(lblFraÅr, 0, 10);
        Label lblTilÅr = new Label("Til år");
        pane.add(lblTilÅr, 0, 12);
        Label lblLeverandør = new Label("Leverandør");
        pane.add(lblLeverandør, 0, 14);

        //Textfields
        TextField txtFadnr = new TextField();
        pane.add(txtFadnr, 0, 1);
        txtFadnr.setPrefWidth(100);
        TextField txtStørrelse = new TextField();
        pane.add(txtStørrelse, 0, 3);
        txtStørrelse.setPrefWidth(100);
        TextField txtAlder = new TextField();
        pane.add(txtAlder, 0, 5);
        txtAlder.setPrefWidth(100);
        TextField txtTidligereIndhold = new TextField();
        pane.add(txtTidligereIndhold, 0, 7);
        txtTidligereIndhold.setPrefWidth(100);
        TextField txtLand = new TextField();
        pane.add(txtLand, 0, 9);
        txtLand.setPrefWidth(100);
        TextField txtFraÅr = new TextField();
        pane.add(txtFraÅr, 0, 11);
        txtFraÅr.setPrefWidth(100);
        TextField txtTilÅr = new TextField();
        pane.add(txtTilÅr, 0, 13);
        txtTilÅr.setPrefWidth(100);
        TextField txtLeverandør = new TextField();
        pane.add(txtLeverandør, 0, 15);
        txtLeverandør.setPrefWidth(100);


        //Knapper
        Button opretFad = new Button("Opret fad");
        pane.add(opretFad, 3, 9);


        Scene scene = new Scene(pane, 300, 600);
        this.setScene(scene);
        this.setTitle("Opret Fad");

        this.show();


        //---------------------------------------------------------------------------------

        //Action til knappen
        opretFad.setOnAction(e -> {
            int størrelse = Integer.parseInt(txtStørrelse.getText());
            int alder = Integer.parseInt(txtAlder.getText());
            String tidligereIndhold = txtTidligereIndhold.getText();
            String land = txtLand.getText();
            int fraÅr = Integer.parseInt(txtFraÅr.getText());
            int tilÅr = Integer.parseInt(txtTilÅr.getText());
            String leverandør = txtLeverandør.getText();
            FadHistorik fadHistorik = new FadHistorik(tidligereIndhold, land, fraÅr, tilÅr, leverandør);
            Controller.createFad(størrelse, alder, fadHistorik);

          //Slet tekst i textfields
            txtFadnr.setText("");
            txtStørrelse.setText("");
            txtAlder.setText("");
            txtTidligereIndhold.setText("");
            txtLand.setText("");
            txtFraÅr.setText("");
            txtTilÅr.setText("");
            txtLeverandør.setText("");

            // Alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Et fad er blevet oprettet!");

            alert.showAndWait();
        });
    }

}
