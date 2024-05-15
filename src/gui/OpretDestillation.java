package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OpretDestillation extends Stage {
    public OpretDestillation() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setHgap(20);
        pane.setVgap(10);


        Label lblDestillationsdato = new Label("Destillationsdato:");
        pane.add(lblDestillationsdato, 0, 0);
        Label lblMaltBatch = new Label("Malt batch:");
        pane.add(lblMaltBatch, 0, 2);
        Label lblKorn = new Label("Korn:");
        pane.add(lblKorn, 0, 4);
        Label lblMedarbejder = new Label("Medarbejder:");
        pane.add(lblMedarbejder, 0, 6);
        Label lblMængdeVæske = new Label("Mængde væske:");
        pane.add(lblMængdeVæske, 0, 8);
        Label lblAlkoholProcent = new Label("Alkoholprocent:");
        pane.add(lblAlkoholProcent, 0, 10);
        Label lblRygeMateriale = new Label("Ryge materiale:");
        pane.add(lblRygeMateriale, 0, 12);
        Label lblKommentar = new Label("Kommentar:");
        pane.add(lblKommentar, 0, 14);


        DatePicker dpDestillationsdato = new DatePicker();
        pane.add(dpDestillationsdato, 0, 1);
        TextField txtMaltBatch = new TextField();
        pane.add(txtMaltBatch, 0, 3);
        ComboBox<String> cbKorn = new ComboBox<>();
        pane.add(cbKorn, 0, 5);
        TextField txtMedarbejder = new TextField();
        pane.add(txtMedarbejder, 0, 7);
        TextField txtMængdeVæske = new TextField();
        pane.add(txtMængdeVæske, 0, 9);
        TextField txtAlkoholProcent = new TextField();
        pane.add(txtAlkoholProcent, 0, 11);
        TextField txtRygeMateriale = new TextField();
        pane.add(txtRygeMateriale, 0, 13);
        TextArea taKommentar = new TextArea();
        pane.add(taKommentar, 0, 15);

        Button opretDestillation = new Button("Opret destillation");
        pane.add(opretDestillation, 0, 16);

        Scene scene = new Scene(pane, 300, 700);
        this.setScene(scene);
        this.setTitle("Opret Destillation");

        this.show();

    }
}
