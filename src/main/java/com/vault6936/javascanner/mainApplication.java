package com.vault6936.javascanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Timer;

import java.io.IOException;
import java.util.TimerTask;

public class mainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        TBAFetcher get = new TBAFetcher();
        MatchDataCollection data = new MatchDataCollection();
        data.Parse(get.httpGetResponse());
        FormattedData formattedData = FormattedData.getBuilder()
                .setActualTime(((MatchData) data.getValue().get(0)).actual_time)
                .setPredictedTime(((MatchData) data.getValue().get(0)).predicted_time)
                .setTime(((MatchData) data.getValue().get(0)).time)
                .build();
        System.out.println(data.getValue().get(0).toString());
        FXMLLoader fxmlLoader = new FXMLLoader(mainApplication.class.getResource("hello-view.fxml"));
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Scene scene = new Scene(fxmlLoader.load(), (int) (screenBounds.getWidth()/1.5), (int) (screenBounds.getHeight()/1.5));
        scene.getStylesheets().add(mainApplication.class.getResource("style.css").toExternalForm());
        Label label1 = new Label();
        label1 = (Label) scene.lookup("#label1");
        label1.setText(formattedData.actual_time.toString());
        Label label2 = new Label();
        label2 = (Label) scene.lookup("#label2");
        label2.setText(formattedData.time.toString());
        Label label3 = new Label();
        label3 = (Label) scene.lookup("#label3");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Match Dashboard");
        primaryStage.show();
        //Timer timer = new Timer();
        //Tasks task = new Tasks(label3, formattedData);
        //timer.schedule(task, 10);
        while(true) {
            formattedData.setCurrentTime();
            label3.setText(formattedData.current_time);
            Platform.runLater(label3.setText("Hello"));
        }
    }

    public static void main(String[] args) {
        launch();
    }
}