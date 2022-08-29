package com.vault6936.javascanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Fetcher get = new Fetcher();
        MatchDataCollection data = new MatchDataCollection();
        data.Parse(get.httpGetResponse());
        System.out.println(data.getValue().get(0).toString());
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Scene scene = new Scene(fxmlLoader.load(), (int) (screenBounds.getWidth()/1.5), (int) (screenBounds.getHeight()/1.5));
        scene.getStylesheets().add(HelloApplication.class.getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Match Dashboard");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}