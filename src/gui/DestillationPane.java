package gui;

import application.controller.Controller;
import application.models.Destillering;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class DestillationPane extends GridPane {
    private ListView<Destillering> destilleringListView;
    private Controller controller;

    public DestillationPane() {
        controller = new Controller();

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        destilleringListView = new ListView<>();

        // Buttons
        Button opretDestillering = new Button("Opret destillering");
        Button tilføjKorn = new Button("Tilføj korn");

        // Listviews
        ObservableList<Destillering> destilleringList = FXCollections.observableArrayList(Controller.getDestilleringer());
        destilleringListView.setItems(destilleringList);

        this.add(opretDestillering, 0, 1);
        this.add(tilføjKorn, 0, 2);
        this.add(destilleringListView, 1, 3);

        // Action for buttons
        opretDestillering.setOnAction(e -> {
            OpretDestillation newWindow = new OpretDestillation();
            newWindow.show();
        });

        tilføjKorn.setOnAction(e -> {
            OpretKorn newWindow = new OpretKorn();
            newWindow.show();
        });

        // Listener for list changes
        destilleringList.addListener((InvalidationListener) observable -> updateDestilleringListView(destilleringList));
    }

    private void updateDestilleringListView(ObservableList<Destillering> destilleringList) {
        destilleringList.clear();
        destilleringList.addAll(Controller.getDestilleringer());
    }
}