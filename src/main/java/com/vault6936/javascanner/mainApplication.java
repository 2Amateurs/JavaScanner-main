package com.vault6936.javascanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.io.IOException;

public class mainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        TBAFetcher get = new TBAFetcher();
        MatchDataCollection data = new MatchDataCollection();
        data.Parse(get.httpGetResponse());
        System.out.println(data.getValue().get(0).toString());
        FXMLLoader fxmlLoader = new FXMLLoader(mainApplication.class.getResource("hello-view.fxml"));
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Scene scene = new Scene(fxmlLoader.load(), (int) (screenBounds.getWidth()/1.5), (int) (screenBounds.getHeight()/1.5));
        scene.getStylesheets().add(mainApplication.class.getResource("style.css").toExternalForm());
        Label label1 = new Label();
        //label1 = (Label) label1.lookup("#testItem");
        label1.setText("hello");
        System.out.println(label1.toString());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Match Dashboard");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}