package gui;

import application.controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class OpretFad extends Stage {

    public OpretFad(String title, Stage owner) {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setHgap(20);
        pane.setVgap(10);


        //Labels og textfields
        Label lblLiterKapacitet = new Label("Liter kapacitet");
        pane.add(lblLiterKapacitet, 0, 2);
        Label lblFraÅr = new Label("Fra år");
        pane.add(lblFraÅr, 0, 4);
        Label lblTidligereIndhold = new Label("Tidligere indhold");
        pane.add(lblTidligereIndhold, 0, 6);
        Label lblLand = new Label("Land");
        pane.add(lblLand, 0, 8);
        Label leverandør1 = new Label("Leverandør");
        pane.add(leverandør1, 0, 10);



        //Textfields
        TextField txtLiterKapacitet = new TextField();
        pane.add(txtLiterKapacitet, 0, 3);
        txtLiterKapacitet.setPrefWidth(100);
        TextField txtFraÅr = new TextField();
        pane.add(txtFraÅr, 0, 5);
        txtFraÅr.setPrefWidth(100);
        TextField txtTidligereIndhold = new TextField();
        pane.add(txtTidligereIndhold, 0, 7);
        txtTidligereIndhold.setPrefWidth(100);
        TextField txtLand = new TextField();
        pane.add(txtLand, 0, 9);
        txtLand.setPrefWidth(100);
        TextField txtLeverandør = new TextField();
        pane.add(txtLeverandør, 0, 11);
        txtLeverandør.setPrefWidth(100);

        //Knapper
        Button opretFad = new Button("Opret fad");
        pane.add(opretFad, 0, 12);


        Scene scene = new Scene(pane, 300, 500);
        this.setScene(scene);
        this.setTitle("Opret Fad");



        //---------------------------------------------------------------------------------

        //Action til knappen
        opretFad.setOnAction(e -> {
            int literKapacitet = Integer.parseInt(txtLiterKapacitet.getText());
//            TODO datepicker?
//            LocalDate tilÅr = LocalDate.parse(txtTilÅr.gettext());
            LocalDate fraÅr = LocalDate.parse(txtFraÅr.getText());
            String tidligereIndhold = txtTidligereIndhold.getText();
            String land = txtLand.getText();;
            String leverandør = txtLeverandør.getText();

//            Controller.createFad(literKapacitet, tidligereIndhold, land, fraÅr, tilÅr, leverandør);

            txtLiterKapacitet.setText("");
            txtFraÅr.setText("");
            txtTidligereIndhold.setText("");
            txtLand.setText("");
            txtLeverandør.setText("");

            this.close();

        });

       }
    }

