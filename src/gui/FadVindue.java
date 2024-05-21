package gui;

import application.controller.Controller;
import application.models.Fad;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class FadVindue extends GridPane {

    private ListView<Fad> lvwTommeFade;
    private ListView<Fad> lvwFyldteFade;
    private Label lblError;
    private Button btnRegistrerNytFad = new Button();
    private Button btnVisHistorik = new Button();
    private Button btnPåfyldFad = new Button();
    private Button btnOmhæld = new Button();
    private KonferenceInputWindow konferenceInputWindow;
    private HotelInputWindow hotelInputWindow;
    private UdflugtInputWindow udflugtInputWindow;
    private UdflugtViewWindow udflugtViewWindow;
    private HotelViewWindow hotelViewWindow;

    public FadVindue() {
        GridPane pane = new GridPane();
        this.add(pane, 0, 0);
        pane.setGridLinesVisible(false);
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setStyle("-fx-border-color: black");

        Label tommeFade = new Label("Tomme fade");
        pane.add(tommeFade, 1, 0);

        lvwTommeFade = new ListView<>();
        pane.add(lvwTommeFade, 1, 1, 2, 5);
        lvwTommeFade.setPrefWidth(250);
        lvwTommeFade.setPrefHeight(200);
        //TODO find tomme fade
        lvwTommeFade.getItems().setAll(Controller.getFade());

        Label lblFyldteFade = new Label("Fyldte fade");
        pane.add(lblFyldteFade, 2, 0);

        lvwFyldteFade = new ListView<>();
        pane.add(lvwFyldteFade, 2, 1, 3, 5);
        lvwFyldteFade.setPrefWidth(250);
        lvwFyldteFade.setPrefHeight(200);
        //TODO find fyldte fade
        lvwFyldteFade.getItems().setAll(Controller.getFade());

        Label lblUdflugter = new Label("Udflugter");
        pane.add(lblUdflugter, 3, 0);

        lvwudflugter = new ListView<>();
        pane.add(lvwudflugter, 3, 1, 4, 4);
        lvwudflugter.setPrefWidth(250);
        lvwudflugter.setPrefHeight(200);

        ChangeListener<Konference> listener = (ov, o, n) -> this.konferenceItemSelected();
        lvwkonference.getSelectionModel().selectedItemProperty().addListener(listener);

        lblError = new Label();
        pane.add(lblError, 1, 8);
        lblError.setStyle("-fx-text-fill: red");

        btnOpretKonference.setText("Opret konference");
        pane.add(btnOpretKonference, 0, 5);

        btnOpretHotel.setText("Opret hotel");
        pane.add(btnOpretHotel, 1, 5);

        btnOpretUdflugt.setText("Opret udflugt");
        pane.add(btnOpretUdflugt, 3, 5);

        btnOpretHotelTilvalg.setText("Opret hoteltilvalg");
        pane.add(btnOpretHotelTilvalg, 1, 6);

        btnVisHotel.setText("Vis hotel");
        pane.add(btnVisHotel, 1, 7);

        btnVisUdflugt.setText("Vis udflugt");
        pane.add(btnVisUdflugt, 3, 6);

        btnOpretKonference.setOnAction(event -> opretKonferenceAction());

        btnOpretHotel.setOnAction(event -> opretHotelAction());

        btnOpretUdflugt.setOnAction(event -> opretUdflugtAction());

        btnOpretHotelTilvalg.setOnAction(event -> opretHotelTilvalgAction());

        btnVisHotel.setOnAction(event -> visHotelAction());

        btnVisUdflugt.setOnAction(event -> visUdflugtAction());

    }

    private void konferenceItemSelected() {
        Konference selected = lvwkonference.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lvwhotel.getItems().setAll(selected.getHoteller());
            lvwudflugter.getItems().setAll(selected.getUdflugter());
        }
    }

    private void opretKonferenceAction() {
        konferenceInputWindow = new KonferenceInputWindow("Opret ny konference", new Stage());
        konferenceInputWindow.showAndWait();
        lvwkonference.getItems().setAll(Controller.getKonferencer());
    }

    private void opretHotelAction() {
        hotelInputWindow = new HotelInputWindow("Opret nyt hotel", new Stage());
        hotelInputWindow.showAndWait();
    }

    private void opretUdflugtAction() {
        udflugtInputWindow = new UdflugtInputWindow("Opret ny udflugt", new Stage());
        udflugtInputWindow.showAndWait();
    }

    private void opretHotelTilvalgAction() {
        Hotel selectedHotel = lvwhotel.getSelectionModel().getSelectedItem();

        if (selectedHotel == null) {
            lblError.setText("Vælg et hotel");
        } else {
            HotelTilvalgWindow hotelTilvalgWindow = new HotelTilvalgWindow("Opret ny hoteltilvalg", new Stage(), selectedHotel);
            hotelTilvalgWindow.showAndWait();
            lblError.setText("");
        }
    }

    private void visHotelAction() {
        hotelViewWindow = new HotelViewWindow("Oversigt over hoteller", new Stage());
        hotelViewWindow.showAndWait();
    }

    private void visUdflugtAction() {
        udflugtViewWindow = new UdflugtViewWindow("Oversigt over udflugter", new Stage());
        udflugtViewWindow.showAndWait();
    }
}
