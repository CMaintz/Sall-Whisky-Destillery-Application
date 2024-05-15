package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPane extends Application {
    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        Label lblUsername = new Label("Username");
        TextField nameInput = new TextField();
        Label lblPassword = new Label("Password");
        PasswordField passwordInput = new PasswordField();
        Label lblHint = new Label("Hint: \nUsername: admin\nPassword: admin");

        Button btnLogin = new Button("Login");
        btnLogin.setOnAction(e -> {
            String username = nameInput.getText();
            String password = passwordInput.getText();
            if (username.equals("admin") && password.equals("admin")) {
                primaryStage.close();

                    try {
                        StartVindue startVindue = new StartVindue();
                        Stage newStage = new Stage();
                        startVindue.start(newStage);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
            }
        });

        gridPane.add(lblUsername, 0, 0);
        gridPane.add(nameInput, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(passwordInput, 1, 1);
        gridPane.add(btnLogin, 1, 2);
        gridPane.add(lblHint, 1, 3);

        Scene scene = new Scene(gridPane, 300, 200);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}