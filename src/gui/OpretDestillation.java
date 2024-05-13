package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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


        DatePicker dpDestillationsdato = new DatePicker();
        pane.add(dpDestillationsdato, 0, 1);


        Scene scene = new Scene(pane, 600, 500);
        this.setScene(scene);
        this.setTitle("Opret Destillation");

        this.show();

    }
}
