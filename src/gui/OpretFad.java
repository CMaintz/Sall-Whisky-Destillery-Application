package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

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
        Label lblFadHistorik = new Label("Fadhistorik");
        pane.add(lblFadHistorik, 0, 6);


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
        TextArea txtFadHistorik = new TextArea();
        pane.add(txtFadHistorik, 0, 7);
        txtFadHistorik.setPrefWidth(200);

        //Knapper
        Button opretFad = new Button("Opret fad");
        pane.add(opretFad, 0, 9);


        Scene scene = new Scene(pane, 300, 500);
        this.setScene(scene);
        this.setTitle("Opret Fad");

        this.show();


        //---------------------------------------------------------------------------------

//        opretFad.setOnAction();
    }

}
