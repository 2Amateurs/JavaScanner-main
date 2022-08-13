package com.vault6936.javascanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
public class Fetcher {
    int teamID = 6936;
    String eventID = "2022alhu"; //should be changed to the most current event
    String URL = "https://www.thebluealliance.com/api/v3/team/frc" + teamID + "/event/" + eventID + "/" + "matches";
    String key = "GAHGTZ290bRxHnbX13UurGfvEgyUaHukRxK2ktrMg2XCNyvykH1IibGqasL3al9I";
    public Fetcher() {}

    public void sendHttpGETRequest() throws IOException {
        URL url = new URL(URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("X-TBA-Auth-Key", key);
        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            //response.append(inputLine);
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            } in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request failed.");
        }
    }
}
