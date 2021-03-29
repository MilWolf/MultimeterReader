package com.github.milwolf.mmreader.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author MilWolf
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("/fxml/MainView.fxml"));
        Parent parent = fxmlLoader.load();
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
}
