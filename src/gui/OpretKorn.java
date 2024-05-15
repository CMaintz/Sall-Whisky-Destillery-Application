package gui;

import application.controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OpretKorn extends Stage {

    public OpretKorn() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setHgap(20);
        pane.setVgap(10);

        //Labels
        Label lblSort = new Label("Korn sort:");
        pane.add(lblSort, 0, 0);
        Label lblVariant = new Label("Korn variant:");
        pane.add(lblVariant, 0, 2);
        Label Marknavn = new Label("Marknavn:");
        pane.add(Marknavn, 0, 4);

        //Textfields
        TextField txtSort = new TextField();
        pane.add(txtSort, 0, 1);
        TextField txtVariant = new TextField();
        pane.add(txtVariant, 0, 3);
        TextField txtMarknavn = new TextField();
        pane.add(txtMarknavn, 0, 5);

        Button opretKorn = new Button("Opret korn");
        pane.add(opretKorn, 0, 6);

        Scene scene = new Scene(pane, 300, 300);
        this.setScene(scene);
        this.setTitle("Opret Korn");
        this.show();


        //----------------------------------------------------------

        //Action til knappen
        opretKorn.setOnAction(e -> {
            String sort = txtSort.getText();
            String variant = txtVariant.getText();
            String marknavn = txtMarknavn.getText();
            Controller.createKorn(sort, variant, marknavn);

            txtSort.setText("");
            txtVariant.setText("");
            txtMarknavn.setText("");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Korn oprettet");
            alert.setHeaderText(null);
            alert.setContentText("Kornet er oprettet");

            alert.showAndWait();


        });
    }
}
