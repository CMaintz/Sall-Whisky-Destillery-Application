package gui;

import application.controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OpretLager extends Stage {

    public OpretLager() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setHgap(20);
        pane.setVgap(10);

        //Labels
        Label lblLagerNavn = new Label("Lager navn:");
        pane.add(lblLagerNavn, 0, 0);

        //Textfields
        TextField txtLagerNavn = new TextField();
        pane.add(txtLagerNavn, 0, 1);

        //Button
        Button opretLager = new Button("Opret lager");
        pane.add(opretLager, 0, 2);

        //Scene
        Scene scene = new Scene(pane, 300, 300);
        this.setScene(scene);
        this.setTitle("Opret Lager");

        //Action til knappen
        opretLager.setOnAction(e -> {
            String lagerNavn = txtLagerNavn.getText();
            Controller.createLager(lagerNavn);

            txtLagerNavn.setText("");
            this.close();
        });

    }

}
