package com.vault6936.javascanner;

import java.time.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import com.vault6936.javascanner.util.TimeFormatting;


public class FormattedData {
    String actual_time;
    String event_key;
    String key;
    long match_number;
    long post_result_time;
    String predicted_time;
    long set_number;
    String time;
    String current_time;

    public FormattedData(String actual_time, long match_number, String predictedTime, String time) {
        this.actual_time = actual_time;
        this.match_number = match_number;
        this.predicted_time = predictedTime;
        this.time = time;
    }
    public void setCurrentTime() {
        ZonedDateTime now = ZonedDateTime.now();
        this.current_time = TimeFormatting.timeToString(now);
    }
    public static class Builder {
        ZonedDateTime actual_time;
        String event_key;
        String key;
        long match_number;
        long post_result_time;
        ZonedDateTime predicted_time;
        long set_number;
        ZonedDateTime time;
        ZoneId zone;
        public Builder() {
            this.zone = TimeZone.getDefault().toZoneId();
        }
        public Builder setActualTime(long time) {
            this.actual_time = ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), this.zone);
            return this;
        }
        public Builder setMatchNumber(int number) {
            this.match_number = number;
            return this;
        }
        public Builder setPredictedTime(long time) {
            this.predicted_time = ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), this.zone);
            return this;
        }
        public Builder setTime(long time) {
            this.time  = ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), this.zone);
            return this;
        }
        public FormattedData build() {
            return new FormattedData(TimeFormatting.timeToString(actual_time), match_number, TimeFormatting.timeToString(predicted_time), TimeFormatting.timeToString(time));
        }
    }
    public static Builder getBuilder() {
        return new Builder();
    }
}
