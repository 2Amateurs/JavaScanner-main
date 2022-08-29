package com.vault6936.javascanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class MatchDataCollection {
    public MatchDataCollection() {}
    ArrayList<MatchData> dataCollection = new ArrayList<>();
    JSONParser parser = new JSONParser();
    public void Parse(String data) {
        try {
            Object obj = parser.parse(data);
            JSONArray jsonObject = (JSONArray) obj;
            jsonObject.toArray();
            for (Object item : jsonObject) {
                addItem((JSONObject) item);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
    public void addItem(JSONObject jsonObject) {
        MatchData object = MatchData.getBuilder()
            .setActualTime((long) jsonObject.get("actual_time"))
            .setEventKey((String) jsonObject.get("event_key"))
            .setKey((String) jsonObject.get("key"))
            .setMatchNumber((long) jsonObject.get("match_number"))
            .setPostResultTime((long) jsonObject.get("post_result_time"))
            .setPredictedTime((long) jsonObject.get("predicted_time"))
            .setSetNumber((long) jsonObject.get("set_number"))
            .setTime((long) jsonObject.get("time"))
            .build();
        dataCollection.add(object);
    }
    public ArrayList getValue() {
        return this.dataCollection;
    }
}
