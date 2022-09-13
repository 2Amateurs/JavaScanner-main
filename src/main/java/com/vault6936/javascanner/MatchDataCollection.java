package com.vault6936.javascanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;

public class MatchDataCollection {
    public MatchDataCollection() {}
    private ArrayList<MatchData> dataCollection = new ArrayList<>();
    JSONParser parser = new JSONParser();
    public void Parse(String[] data) {
        try {
            Object obj1 = parser.parse(data[0]);
            Object obj2 = parser.parse(data[1]);
            JSONArray jsonObject1 = (JSONArray) obj1;
            JSONObject jsonObject2 = (JSONObject) ( (JSONObject) obj2 ).get("qual");
            JSONObject jsonObject3 = (JSONObject) jsonObject2.get("ranking");
            long rank = (long) jsonObject3.get("rank");
            jsonObject1.toArray();
            System.out.println(rank);
            for (Object item : jsonObject1) {
                addItem((JSONObject) item, rank);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
    public void addItem(JSONObject jsonObject1, long rank) {
        MatchData object = MatchData.getBuilder()
            .setActualTime((long) jsonObject1.get("actual_time"))
            .setEventKey((String) jsonObject1.get("event_key"))
            .setKey((String) jsonObject1.get("key"))
            .setMatchNumber((long) jsonObject1.get("match_number"))
            .setPostResultTime((long) jsonObject1.get("post_result_time"))
            .setPredictedTime((long) jsonObject1.get("predicted_time"))
            .setSetNumber((long) jsonObject1.get("set_number"))
            .setTime((long) jsonObject1.get("time"))
            .setRank(rank)
            .build();
        dataCollection.add(object);
    }
    public MatchData get(int index) {
        return (MatchData) this.dataCollection.get(index);
    }
    public long getLength() {
        return this.dataCollection.size();
    }
    public Integer findUpcoming(long currentTime) {
        Integer index = null;
        for(int i = 0; i < dataCollection.size(); i++) {
            if((dataCollection.get(i).time > currentTime) && index == null) {
                index = Integer.valueOf(i);
                return index;
            }
        }
        return null;
    }
}
