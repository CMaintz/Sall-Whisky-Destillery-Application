package gui;

import application.controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class DestillationPane extends GridPane {
    private Controller controller;

    public DestillationPane() {
//        controller = Controller.getController();

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        // Add components here


    }

}
