package com.vault6936.javascanner;

import com.vault6936.javascanner.util.FXHelper;
import com.vault6936.javascanner.util.TimeFormatting;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class mainApplication extends Application {
    MatchDataCollection data = new MatchDataCollection();
    FormattedData currentMatchFormatted;
    FormattedData nextMatchFormatted;
    FXMLLoader fxmlLoader = new FXMLLoader(mainApplication.class.getResource("window.fxml"));
    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    Scene scene = new Scene(fxmlLoader.load(), (int) (screenBounds.getWidth()/1.1), (int) (screenBounds.getHeight()/1.1));
    FXHelper<VBox> vBoxGetter = new FXHelper<>(scene);
    VBox body = vBoxGetter.getNode("#body");
    FXHelper<Text> textGetter = new FXHelper<>(scene);
    Text nextMatchTime = textGetter.getNode("#nextMatchTime");
    Text matchTime2 = textGetter.getNode("#matchTime2");
    Text countdown = textGetter.getNode("#countdown");
    Text matchNumber = textGetter.getNode("#matchNumber");
    Text clockDisplay = textGetter.getNode("#clock");
    Text smallMatchNumber2 = textGetter.getNode("#smallMatchNumber2");
    Text rank = textGetter.getNode("#rank");
    Text partner1 = textGetter.getNode("#partner1");
    Text partner2 = textGetter.getNode("#partner2");
    Timer clockHandler = new Timer();
    clockUpdater clock;
    Timer refreshHandler = new Timer();
    Refresher refresh;

    public mainApplication() throws IOException{

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        scene.getStylesheets().add(Objects.requireNonNull(mainApplication.class.getResource("style.css")).toExternalForm());
        primaryStage.getIcons().add(new Image("file:Icons\\Icon2.png"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Match Dashboard");
        primaryStage.show();
        data.Parse(Objects.requireNonNull(TBAFetcher.httpGetResponse()));
        refresh = new Refresher();
        refresh.initialize();
        refreshHandler.schedule(refresh, 30000, 30000); //refreshes data every 30s
        clock = new clockUpdater();
        clockHandler.schedule(clock, 0, 1000); //updates clock every second
    }
    private class Refresher extends TimerTask {

        public void initialize() throws IOException {
            try {
                data.Parse(Objects.requireNonNull(TBAFetcher.httpGetResponse()));
            } catch (IOException e) {
                throw new RuntimeException("TBA didn't respond :(");
            }
            Integer index = data.findUpcoming(TimeFormatting.getCurrentTime());
            int currentMatch;
            int nextMatch;
            if (index == null) {
                System.out.println("No upcoming matches!");
                currentMatch = 3;
                nextMatch = 5;
            } else {
                currentMatch = index.intValue();
                if(currentMatch < (data.getLength()-1)) {
                    nextMatch = currentMatch + 1;
                } else {
                    nextMatch = 0;
                }
            }
            MatchData currentMatchData = data.get(currentMatch);
            currentMatchData.setPartners();
            MatchData nextMatchData = data.get(nextMatch);
            currentMatchFormatted = FormattedData.getBuilder()
                    .setActualTime(currentMatchData.actual_time)
                    .setPredictedTime(currentMatchData.predicted_time)
                    .setTime(currentMatchData.time)
                    .setMatchNumber(currentMatchData.match_number)
                    .setRank(currentMatchData.rank)
                    .setAlliance(currentMatchData.myAlliance)
                    .setPartners(currentMatchData.partnerNames)
                    .build();
            nextMatchFormatted = FormattedData.getBuilder()
                    .setActualTime(nextMatchData.actual_time)
                    .setPredictedTime(nextMatchData.predicted_time)
                    .setTime(nextMatchData.time)
                    .setMatchNumber(nextMatchData.match_number)
                    .build();
            if (Objects.equals(currentMatchFormatted.alliance, "blue")) {
                body.setStyle("-fx-background-color: #3470d1");
            } else {
                body.setStyle("-fx-background-color: #d62e2e");
            }
            nextMatchTime.setText(currentMatchFormatted.predicted_time);
            matchTime2.setText(nextMatchFormatted.predicted_time);
            matchNumber.setText(currentMatchFormatted.match_number);
            smallMatchNumber2.setText(nextMatchFormatted.match_number);
            rank.setText(currentMatchFormatted.rank);
            partner1.setText(currentMatchFormatted.partners[0]);
            partner2.setText(currentMatchFormatted.partners[1]);
            System.out.println(currentMatchFormatted.rank);
        }
        public void run() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        initialize();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }
    private class clockUpdater extends TimerTask {
        public void initialize() {
            currentMatchFormatted.update();
            clockDisplay.setText(currentMatchFormatted.current_time);
            countdown.setText(currentMatchFormatted.timeUntil);
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