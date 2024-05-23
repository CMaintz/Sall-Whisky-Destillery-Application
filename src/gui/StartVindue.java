package gui;

import application.controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class StartVindue extends Application {
    private Controller controller;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Sall Whisky Distillery");
        BorderPane pane = new BorderPane();
        this.initContent(pane);

        Image image = new Image("https://sallwhisky.com/wp-content/uploads/2021/01/Fad-tegnet.png"); // replace with your image path

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);

        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        Background background = new Background(backgroundImage);
        pane.setBackground(background);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(1000);
        stage.show();
    }

    private void initContent(BorderPane pane) {
        TabPane tabPane = new TabPane();
        this.initTabPane(tabPane);
        pane.setCenter(tabPane);
    }

    private void initTabPane(TabPane tabPane) {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tabFade = new Tab("Fade");
        Tab tabDestillation = new Tab("Destillering");
        Tab tabLager = new Tab("Lagerstyring");
        Tab tabWhisky = new Tab("Whisky");

        FadVindue fadVindue = new FadVindue();
        tabFade.setContent(fadVindue);
        DestilleringPane destilleringPane = new DestilleringPane();
        tabDestillation.setContent(destilleringPane);
        LagerstyringPane lagerstyringPane = new LagerstyringPane();
        tabLager.setContent(lagerstyringPane);
        WhiskyPane whiskyPane = new WhiskyPane();
        tabWhisky.setContent(whiskyPane);

        tabPane.getTabs().add(tabFade);
        tabPane.getTabs().add(tabDestillation);
        tabPane.getTabs().add(tabLager);
        tabPane.getTabs().add(tabWhisky);

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == tabDestillation) {
                DestilleringPane newDestilleringPane = new DestilleringPane();
                tabDestillation.setContent(newDestilleringPane);
            }
        });

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == tabFade) {
                FadVindue newFadVindue = new FadVindue();
                tabFade.setContent(fadVindue);
            }
        });
    }
    }

