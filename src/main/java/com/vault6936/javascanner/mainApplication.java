package com.vault6936.javascanner;

import com.vault6936.javascanner.util.FXHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.io.IOException;
import java.util.Timer;

public class mainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        TBAFetcher get = new TBAFetcher();
        MatchDataCollection data = new MatchDataCollection();
        data.Parse(get.httpGetResponse());
        FormattedData formattedData = FormattedData.getBuilder()
                .setActualTime(data.get(0).actual_time)
                .setPredictedTime(data.get(0).predicted_time)
                .setTime(data.get(0).time)
                .setMatchNumber(data.get(0).match_number)
                .build();
        System.out.println(data.get(0).toString());
        FXMLLoader fxmlLoader = new FXMLLoader(mainApplication.class.getResource("window.fxml"));
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Scene scene = new Scene(fxmlLoader.load(), (int) (screenBounds.getWidth()/1.5), (int) (screenBounds.getHeight()/1.5));
        scene.getStylesheets().add(mainApplication.class.getResource("style.css").toExternalForm());
        FXHelper<Text> textGetter = new FXHelper<>(scene); //I was tired of having to cast each thing to the correct Node every time, so I made this.  Don't judge me.
        Text nextMatchTime = textGetter.getNode("#nextMatchTime");
        Text countdown = textGetter.getNode("#countdown");
        Text matchNumber = textGetter.getNode("#matchNumber");
        nextMatchTime.setText(formattedData.predicted_time);
        matchNumber.setText(formattedData.match_number);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Match Dashboard");
        primaryStage.show();
        Timer timer = new Timer();
        clockUpdate clock = new clockUpdate(primaryStage, countdown, formattedData);
        timer.schedule(clock, 0, 1000);
    }

    public static void main(String[] args) {
        launch();
    }
}