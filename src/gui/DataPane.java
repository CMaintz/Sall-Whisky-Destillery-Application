package gui;

import application.controller.Controller;
import application.models.Fad;
import application.models.FadHistorik;
import application.models.Lager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import storage.Storage;

public class DataPane extends GridPane {
    private Controller controller;

    private ListView<Fad> fadListView;

    public DataPane() {
        controller = new Controller();
        controller.createSomeObjects();

        fadListView = new ListView<Fad>();

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label fadLabel = new Label("Fyldte fade:");


        //Knapper

        Button visFadData = new Button("Vis Faddata og historik");


        //Listviews
        ObservableList<Fad> fadList = FXCollections.observableArrayList(Storage.getFade());
        fadListView.setItems(fadList.filtered(fad -> fad.getDestillat() != null));

        this.add(fadListView, 0, 3);
        this.add(fadLabel, 0, 2);
        this.add(visFadData, 0, 4);

        //Action til knappen
        visFadData.setOnAction(e -> {
            Fad selectedFad = fadListView.getSelectionModel().getSelectedItem();

            if(selectedFad != null) {
                FadHistorik fadHistorik = selectedFad.getFadHistorik();

                Stage stage = new Stage();
                stage.setTitle("Fad Historik");

                TextArea textArea = new TextArea(fadHistorik.toString());
                textArea.setEditable(false);

                Scene scene = new Scene(textArea,300,200);
                stage.setScene(scene);

                stage.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fejl");
                alert.setHeaderText(null);
                alert.setContentText("Vælg et fad først");
                alert.showAndWait();
            }
        });
    }
}
