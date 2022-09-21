package com.vault6936.javascanner;

import java.time.*;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import com.vault6936.javascanner.util.TimeFormatting;


public class FormattedData {
    String actual_time;
    String match_number;
    String predicted_time;
    String time;
    String current_time;
    String timeUntil;
    String rank;
    String alliance;
    String[] partners;
    ZonedDateTime predictedTime_noFormat;
    ZonedDateTime currentTime_noFormat;
    public FormattedData(String actual_time, String match_number, ZonedDateTime predictedTime, String time, String rank, String alliance, String[] partners) {
        this.actual_time = actual_time;
        this.match_number = match_number;
        this.predicted_time = TimeFormatting.timeToString(predictedTime);
        this.predictedTime_noFormat = predictedTime;
        this.time = time;
        this.rank = rank;
        this.alliance = alliance;
        this.partners = partners;
    }
    public void setCurrentTime() {
        ZonedDateTime now = ZonedDateTime.now();
        this.currentTime_noFormat = now;
        this.current_time = TimeFormatting.timeToString(now);
    }
    public void update() {
        setCurrentTime();
        this.timeUntil = TimeFormatting.timeUntil(currentTime_noFormat, predictedTime_noFormat);
    }
    public static class Builder {
        ZonedDateTime actual_time;
        String match_number;
        ZonedDateTime predicted_time;
        ZonedDateTime time;
        ZoneId zone;
        long rank;
        String alliance;
        String[] partners;
        public Builder() {
            this.zone = TimeZone.getDefault().toZoneId();
        }
        public Builder setActualTime(long time) {
            this.actual_time = ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), this.zone);
            return this;
        }
        public Builder setMatchNumber(long number) {
            this.match_number = String.valueOf(number);
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
        public Builder setRank(long rank) {
            this.rank = rank;
            return this;
        }
        public Builder setAlliance(String alliance) {
            this.alliance = alliance;
            return this;
        }
        public Builder setPartners(String[] partners) {
            this.partners = partners;
            return this;
        }
        public FormattedData build() {
            return new FormattedData(TimeFormatting.timeToString(actual_time), match_number, predicted_time, TimeFormatting.timeToString(time), String.valueOf(rank), alliance, partners);
        }
    }
    public static Builder getBuilder() {
        return new Builder();
    }
}
