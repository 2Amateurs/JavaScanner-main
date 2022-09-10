package com.vault6936.javascanner;

import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class clockUpdate extends TimerTask {
    Stage stage;
    Text text;
    FormattedData data;
    public clockUpdate(Stage stage, Text label, FormattedData data) {
        this.text = label;
        this.data = data;
        this.stage = stage;
    }
    private void set() {
        data.update();
        text.setText(data.timeUntil);
    }
    @Override
    public void run() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                set();
            }
        });
    }
}
