package com.vault6936.javascanner;

import com.vault6936.javascanner.util.FXHelper;
import com.vault6936.javascanner.util.TimeFormatting;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class mainApplication extends Application {
    Stage primaryStage;
    TBAFetcher get = new TBAFetcher();
    MatchDataCollection data = new MatchDataCollection();
    FormattedData formattedData1;
    FormattedData formattedData2;
    FXMLLoader fxmlLoader = new FXMLLoader(mainApplication.class.getResource("window.fxml"));
    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    Scene scene = new Scene(fxmlLoader.load(), (int) (screenBounds.getWidth()/1.1), (int) (screenBounds.getHeight()/1.1));
    FXHelper<Text> textGetter = new FXHelper<>(scene);
    Text nextMatchTime = textGetter.getNode("#nextMatchTime");
    Text matchTime2 = textGetter.getNode("#matchTime2");
    Text countdown = textGetter.getNode("#countdown");
    Text matchNumber = textGetter.getNode("#matchNumber");
    Text clockDisplay = textGetter.getNode("#clock");
    Text smallMatchNumber2 = textGetter.getNode("#smallMatchNumber2");
    Timer clockHandler = new Timer();
    clockUpdater clock;
    Timer refreshHandler = new Timer();
    Refresher refresh;

    public mainApplication() throws IOException{

    }

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        scene.getStylesheets().add(mainApplication.class.getResource("style.css").toExternalForm());
        primaryStage.getIcons().add(new Image("file:Icons\\Icon2.png"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Match Dashboard");
        primaryStage.show();
        data.Parse(get.httpGetResponse());
        refresh = new Refresher();
        refresh.initialize();
        refreshHandler.schedule(refresh, 0, 30000);
        clock = new clockUpdater();
        clockHandler.schedule(clock, 0, 1000);
    }
    private class Refresher extends TimerTask {

        public void initialize() {
            try {
                data.Parse(get.httpGetResponse());
            } catch (IOException e) {
                throw new RuntimeException("TBA didn't respond :(");
            }
            Integer index = data.findUpcoming(TimeFormatting.getCurrentTime());
            int currentMatch;
            int nextMatch;
            if (index == null) {
                System.out.println("No upcoming matches!");
                currentMatch = 0;
                nextMatch = 5;
            } else {
                currentMatch = index.intValue();
                if(currentMatch < (data.getLength()-1)) {
                    nextMatch = currentMatch + 1;
                } else {
                    nextMatch = 0;
                }
            }
            formattedData1 = FormattedData.getBuilder()
                    .setActualTime(data.get(currentMatch).actual_time)
                    .setPredictedTime(data.get(currentMatch).predicted_time)
                    .setTime(data.get(currentMatch).time)
                    .setMatchNumber(data.get(currentMatch).match_number)
                    .setRank(data.get(currentMatch).rank)
                    .build();
            formattedData2 = FormattedData.getBuilder()
                    .setActualTime(data.get(nextMatch).actual_time)
                    .setPredictedTime(data.get(nextMatch).predicted_time)
                    .setTime(data.get(nextMatch).time)
                    .setMatchNumber(data.get(nextMatch).match_number)
                    .build();
            nextMatchTime.setText(formattedData1.predicted_time);
            matchTime2.setText(formattedData2.predicted_time);
            matchNumber.setText(formattedData1.match_number);
            smallMatchNumber2.setText(formattedData2.match_number);
            System.out.println(formattedData1.rank);
        }
        public void run() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    initialize();
                }
            });
        }
    }
    private class clockUpdater extends TimerTask {
        public void initialize() {
            formattedData1.update();
            clockDisplay.setText(formattedData1.current_time);
            countdown.setText(formattedData1.timeUntil);
        }
        public void run() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    initialize();
                }
            });
        }
    }

    public static void main(String[] args) {
        launch();
    }
}