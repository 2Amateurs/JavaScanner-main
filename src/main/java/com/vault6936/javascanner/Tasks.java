package com.vault6936.javascanner;

import java.util.TimerTask;
import javafx.scene.*;
import javafx.scene.control.Label;

public class Tasks extends TimerTask {
    Label label;
    FormattedData data;
    public Tasks(Label label, FormattedData data) {
        this.label = label;
        this.data = data;
    }
    @Override
    public void run() {
        data.setCurrentTime();
        label.setText(data.current_time);
    }
}
