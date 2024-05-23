package gui;

import application.models.Lager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OpretReol extends Stage {
    private Lager lager;
    private TextField txfAntalReoler, txfAntalHylder;
    public OpretReol(String title, Stage owner, Lager lager) {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setHgap(20);
        pane.setVgap(10);
        Scene scene = new Scene(pane, 300, 300);
        this.setScene(scene);
        this.setTitle(title);
        this.lager = lager;

        Label lblAntalReoler = new Label("Antal Reoler");
        pane.add(lblAntalReoler, 0, 0);

        Label lblAntalHylder = new Label("Antal Hylder pr reol");
        pane.add(lblAntalHylder, 0, 1);

        txfAntalReoler = new TextField();
        pane.add(txfAntalReoler, 1, 0);
        txfAntalReoler.setPrefWidth(100);

        txfAntalHylder = new TextField();
        pane.add(txfAntalHylder, 1, 1);
        txfAntalHylder.setPrefWidth(100);

        Button btnOk = new Button("Ok");
        pane.add(btnOk, 0, 3);

        btnOk.setOnAction(event -> okAction());
    }

    private void okAction() {
        try {
            int antalReoler = Integer.parseInt(txfAntalReoler.getText().trim());
            try {
                int antalHylder = Integer.parseInt(txfAntalHylder.getText().trim());
                for (int i = 0; i < antalReoler; i++) {
                    lager.createReol(antalHylder);
                }
                this.close();
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid number for antal Hylder");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid number for antal reoler");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
