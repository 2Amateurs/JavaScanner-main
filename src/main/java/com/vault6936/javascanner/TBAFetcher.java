package com.vault6936.javascanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
public class TBAFetcher {
    public static int teamID = 6936;
    String eventID = "2022alhu"; //should be changed to the most current event
    String URL1 = "https://www.thebluealliance.com/api/v3/team/frc" + teamID + "/event/" + eventID + "/" + "matches";
    String URL2 = "https://www.thebluealliance.com/api/v3/team/frc" + teamID + "/event/" + eventID + "/" + "status";
    String key = "GAHGTZ290bRxHnbX13UurGfvEgyUaHukRxK2ktrMg2XCNyvykH1IibGqasL3al9I";
    public TBAFetcher() {}

    public String[] httpGetResponse() throws IOException {
        System.out.println("Running method");
        String response1;
        String response2;
        StringBuffer response;
        BufferedReader in;
        String inputLine;
        URL url = new URL(URL1);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("X-TBA-Auth-Key", key);
        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            response = new StringBuffer();

            //response.append(inputLine);
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            } in.close();
            response1 = response.toString();
        } else {
            return null;
        }
        url = new URL(URL2);
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("X-TBA-Auth-Key", key);
        responseCode = httpURLConnection.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            response = new StringBuffer();

            //response.append(inputLine);
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            } in.close();
            response2 = response.toString();
        } else {
            return null;
        }
        return new String[]{response1, response2};
    }
}
