package com.vault6936.javascanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;


public class MatchData {

    long actual_time;
    String event_key;
    String key;
    long match_number;
    long post_result_time;
    long predicted_time;
    long set_number;
    long time;
    long rank;
    String myAlliance;
    String[] partnerIDs;
    String[] partnerNames;

    private MatchData(long actual_time, String event_key, String key, long match_number, long post_result_time, long predicted_time, long set_number, long time, long rank, String myAlliance, String[] partnerIDs) {
        this.actual_time = actual_time;
        this.event_key = event_key;
        this.key = key;
        this.match_number = match_number;
        this.post_result_time = post_result_time;
        this.predicted_time = predicted_time;
        this.set_number = set_number;
        this.time = time;
        this.rank = rank;
        this.myAlliance = myAlliance;
        this.partnerIDs = partnerIDs;
    }
    private String[] parseNameData(String[] data) {
        JSONParser parser = new JSONParser();
        String team1Name;
        String team2Name;
        try {
            Object team1Obj = parser.parse(data[0]);
            Object team2Obj = parser.parse(data[1]);
            JSONObject team1 = (JSONObject) team1Obj;
            JSONObject team2 = (JSONObject) team2Obj;
            team1Name = team1.get("nickname").toString();
            team2Name = team2.get("nickname").toString();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new String[]{team1Name, team2Name};
    }
    public void setPartners() throws IOException {
        this.partnerNames = parseNameData(TBAFetcher.rawAlliancePartnerInfo(partnerIDs));
    }
    public static class Builder {
        long actual_time;
        String event_key;
        String key;
        long match_number;
        long post_result_time;
        long predicted_time;
        long set_number;
        long time;
        long rank;
        String myAlliance;
        String[] partnerIDs;

        public Builder setActualTime(long time) {
            this.actual_time = time;
            return this;
        }
        public Builder setEventKey(String key) {
            this.event_key = key;
            return this;
        }
        public Builder setKey(String key) {
            this.key = key;
            return this;
        }
        public Builder setMatchNumber(long number) {
            this.match_number = number;
            return this;
        }
        public Builder setPostResultTime(long time) {
            this.post_result_time = time;
            return this;
        }
        public Builder setPredictedTime(long time) {
            this.predicted_time = time;
            return this;
        }
        public Builder setSetNumber(long number) {
            this.set_number = set_number;
            return this;
        }
        public Builder setTime(long time) {
            this.time = time;
            return this;
        }
        public Builder setRank(long rank) {
            this.rank = rank;
            return this;
        }
        public Builder setAlliance(String alliance) {
            this.myAlliance = alliance;
            return this;
        }
        public Builder setPartnerIDs(String[] partnerIDs) {
            this.partnerIDs = partnerIDs;
            return this;
        }
        public MatchData build () {
            return new MatchData(actual_time*1000, event_key, key, match_number, post_result_time*1000, predicted_time*1000, set_number, time*1000, rank, myAlliance, partnerIDs);
        }

    }
    public static Builder getBuilder() {
        return new Builder();
    }
    @Override
    public String toString() {
        String string = "actual time: " + String.valueOf(actual_time) + "\n";
        string += "event key: " + event_key + "\n";
        string += "key: " + key + "\n";
        string += "match number: " + String.valueOf(match_number) + "\n";
        string += "post result time: " + String.valueOf(post_result_time) + "\n";
        string += "predicted time: " + String.valueOf(predicted_time) + "\n";
        string += "set number: " + String.valueOf(set_number) + "\n";
        string += "time: " + String.valueOf(time) + "\n";
        return string;
    }
}
