package gui;

import application.models.Destillat;
import application.models.Fad;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.util.List;

public class VisHistorik extends Stage {
    public VisHistorik(String title, Stage owner, Fad fad) {
        this.fad = fad;
        this.initOwner(owner);
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setMinHeight(100);
        this.setMinWidth(200);
        this.setResizable(false);

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }
    private TextField txfTidligereIndhold, txfLand, txfFraÅr, txfTilÅr, txfLeverandør;
    private ListView<Destillat> lvwDestillater;
    private Button btnOk = new Button();
    private Fad fad;
    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblTidligereIndhold = new Label("Tidligere Indhold");
        pane.add(lblTidligereIndhold, 0, 0);

        Label lblLand = new Label("Land");
        pane.add(lblLand, 0, 1);

        Label lblFraÅr = new Label("FraÅr");
        pane.add(lblFraÅr, 0, 2);

        Label lblLeverandør = new Label("Leverandør");
        pane.add(lblLeverandør, 0, 4);

        Label lblDestillater = new Label("Tidligere Destillater");
        pane.add(lblDestillater, 0, 5);

        txfTidligereIndhold = new TextField();
        pane.add(txfTidligereIndhold, 1, 0);
        txfTidligereIndhold.setPrefWidth(185);
        txfTidligereIndhold.setText(fad.getFadHistorik().getTidligereIndhold());
        txfTidligereIndhold.setEditable(false);


        txfLand = new TextField();
        pane.add(txfLand, 1, 1);
        txfLand.setPrefWidth(185);
        txfLand.setText(fad.getFadHistorik().getLand());
        txfLand.setEditable(false);

        txfFraÅr = new TextField();
        pane.add(txfFraÅr, 1, 2);
        txfFraÅr.setPrefWidth(185);
        txfFraÅr.setText(fad.getFadHistorik().getFraÅr().toString());
        txfFraÅr.setEditable(false);


        txfLeverandør = new TextField();
        pane.add(txfLeverandør, 1, 4);
        txfLeverandør.setPrefWidth(185);
        txfLeverandør.setText(fad.getFadHistorik().getLeverandør());
        txfLeverandør.setEditable(false);


        lvwDestillater = new ListView<>();
        pane.add(lvwDestillater, 1, 5, 2, 5);
        lvwDestillater.getItems().setAll(fad.getFadHistorik().getTidligereDestillater());


        btnOk.setText("Luk vindue");
        pane.add(btnOk, 0, 8);

        btnOk.setOnAction(event -> okAction());
    }

    private void okAction() {
        this.hide();
    }
}
