package gui;

import application.controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;

public class StartVindue extends Application {
    private Controller controller;

    @Override
    public void init() {
//        controller = Controller.getController();
//        controller.createSomeObjects();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Sall Whisky Distillery");
        BorderPane pane = new BorderPane();
        this.initContent(pane);

        // Load the image
        Image image = new Image("https://sallwhisky.com/wp-content/uploads/2021/01/Fad-tegnet.png"); // replace with your image path

        // Create a BackgroundSize object
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);

        // Create a BackgroundImage object
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        // Create a Background object and set it to the pane
        Background background = new Background(backgroundImage);
        pane.setBackground(background);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(800);
        stage.show();
    }

    private void initContent(BorderPane pane) {
        TabPane tabPane = new TabPane();
        this.initTabPane(tabPane);
        pane.setCenter(tabPane);
    }

    private void initTabPane(TabPane tabPane) {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tabForside = new Tab("Forside");
        Tab tabDestillation = new Tab("Destillation");
        Tab tabLager = new Tab("Lagerstyring");
        Tab tabData = new Tab("Hent Faddata");


//        DestillationPane destillationPane = new DestillationPane();
//        tabDestillation.setContent(destillationPane);
//        LagerstyringPane lagerstyringPane = new LagerstyringPane();
//        tabLager.setContent(lagerstyringPane);
//        DataPane dataPane = new DataPane();
//        tabData.setContent(dataPane);

        tabPane.getTabs().add(tabForside);
        tabPane.getTabs().add(tabDestillation);
        tabPane.getTabs().add(tabLager);
        tabPane.getTabs().add(tabData);

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == tabDestillation) {
                DestillationPane destillationPane = new DestillationPane();
                tabDestillation.setContent(destillationPane);
            }
        });

//        tabForside.setOnSelectionChanged(event -> forsidePane.updateControls());
//        tabDestillation.setOnSelectionChanged(event -> destillationPane.updateControls());
//        tabLager.setOnSelectionChanged(event -> lagerstyringPane.updateControls());
//        tabData.setOnSelectionChanged(event -> dataPane.updateControls());
//
    }
}
