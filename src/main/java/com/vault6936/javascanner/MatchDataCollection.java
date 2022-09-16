package com.vault6936.javascanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;

public class MatchDataCollection {
    public MatchDataCollection() {}
    private final ArrayList<MatchData> dataCollection = new ArrayList<>();
    JSONParser parser = new JSONParser();
    public void Parse(String[] data) {
        try {
            Object matchInfo = parser.parse(data[0]);
            Object rankInfo = parser.parse(data[1]);
            JSONArray matches = (JSONArray) matchInfo;
            JSONObject qual = (JSONObject) ( (JSONObject) rankInfo ).get("qual");
            JSONObject rankings = (JSONObject) qual.get("ranking");
            long rank = (long) rankings.get("rank");
            System.out.println(rank);
            for (Object item : matches) {
                addItem((JSONObject) item, rank);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
    private String findAlliance(Object[] blueTeamKeys, Object[] redTeamKeys) {
        String myAlliance = null;
        for(Object key : blueTeamKeys) {
            System.out.println(key.toString());
            if(key.toString().equals("frc" + TBAFetcher.teamID)) {
                myAlliance = "blue";
            }
        }
        if (myAlliance == "blue") {
            return "blue";
        }
        else {
            return "red";
        }
    }
    public void addItem(JSONObject match, long rank) {
        JSONObject alliances = (JSONObject) match.get("alliances");
        JSONObject blueAlliance = (JSONObject) alliances.get("blue");
        JSONObject redAlliance = (JSONObject) alliances.get("red");
        Object[] blueTeamKeys = ((JSONArray) blueAlliance.get("team_keys")).toArray();
        Object[] redTeamKeys = ((JSONArray) redAlliance.get("team_keys")).toArray();
        System.out.println(findAlliance(blueTeamKeys, redTeamKeys));
        MatchData object = MatchData.getBuilder()
            .setActualTime((long) match.get("actual_time"))
            .setEventKey((String) match.get("event_key"))
            .setKey((String) match.get("key"))
            .setMatchNumber((long) match.get("match_number"))
            .setPostResultTime((long) match.get("post_result_time"))
            .setPredictedTime((long) match.get("predicted_time"))
            .setSetNumber((long) match.get("set_number"))
            .setTime((long) match.get("time"))
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
                index = i;
                return index;
            }
        }
        return null;
    }
}
