package gui;

import application.controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;

public class DestillationPane extends GridPane {
    private Controller controller;

    public DestillationPane() {
//        controller = Controller.getController();

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //Knapper
        Button opretFad = new Button("Opret fad");
        opretFad.setOnAction(e -> {
                    OpretFad newWindow = new OpretFad();
                });
        Button opretDestillation = new Button("Opret destillation");
        opretDestillation.setOnAction(e -> {
                    OpretDestillation newWindow = new OpretDestillation();
                });


        this.add(opretFad, 0, 0);
        this.add(opretDestillation, 0, 1);






    }

}
