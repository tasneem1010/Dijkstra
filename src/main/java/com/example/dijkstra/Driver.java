package com.example.dijkstra;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.net.URISyntaxException;


public class Driver extends Application {
    /**
     * start application
     * @param stage main stage
     * @throws Exception
     */

    @Override
    public void start(Stage stage) throws URISyntaxException {
        MainScreen menu = new MainScreen();
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
        menu.show(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
