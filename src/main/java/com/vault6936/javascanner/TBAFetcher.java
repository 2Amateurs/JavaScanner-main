package com.vault6936.javascanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
public class TBAFetcher {
    public static int teamID = 6936;
    static String eventID = "2022alhu"; //should be changed to the most current event
    static String URL1 = "https://www.thebluealliance.com/api/v3/team/frc" + teamID + "/event/" + eventID + "/" + "matches";
    static String URL2 = "https://www.thebluealliance.com/api/v3/team/frc" + teamID + "/event/" + eventID + "/" + "status";
    static String key = "GAHGTZ290bRxHnbX13UurGfvEgyUaHukRxK2ktrMg2XCNyvykH1IibGqasL3al9I";
    private static String fallBack1 = Constants.noInternetFallback1;
    private static String fallBack2 = Constants.noInternetFallback2;
    private static String fallBack3 = Constants.noInternetFallback3;
    private static String fallBack4 = Constants.noInternetFallback4;
    public TBAFetcher() {}

    public static String[] httpGetResponse() throws IOException {
        String response1;
        String response2;
        StringBuffer response;
        BufferedReader in;
        String inputLine;
        URL url = new URL(URL1);
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("X-TBA-Auth-Key", key);
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                } in.close();
                response1 = response.toString();
                fallBack1 = response1;
            } else {
                System.out.println("GET failed with error code " + responseCode);
                response1 = fallBack1;
            }
            url = new URL(URL2);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("X-TBA-Auth-Key", key);
            responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                System.out.println("Response code 200.  Huzzah!");
                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                } in.close();
                response2 = response.toString();
                fallBack2 = response2;
            } else {
                System.out.println("GET failed with error code " + responseCode);
                response2 = fallBack2;
            }
            return new String[]{response1, response2};
        } catch (UnknownHostException e) {
            System.out.println("No internet connection.");
        } catch (Exception e)
        {
            System.out.println("Unexpected exception : " + e);
        }
        return new String[]{fallBack1, fallBack2};
    }
    public static String[] rawAlliancePartnerInfo(String[] teamNums) throws IOException {
        String URL1 = "https://www.thebluealliance.com/api/v3/team/" + "frc" + teamNums[0];
        String URL2 = "https://www.thebluealliance.com/api/v3/team/" + "frc" + teamNums[1];
        String response1;
        String response2;
        StringBuffer response;
        BufferedReader in;
        String inputLine;
        URL url = new URL(URL1);
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("X-TBA-Auth-Key", key);
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                response1 = response.toString();
                fallBack3 = response1;
            } else {
                System.out.println("GET failed with error code " + responseCode);
                response1 = fallBack3;
            }
            url = new URL(URL2);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("X-TBA-Auth-Key", key);
            responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                response2 = response.toString();
                fallBack4 = response2;
            } else {
                System.out.println("GET failed with error code " + responseCode);
                response2 = fallBack4;
            }
            return new String[]{response1, response2};
        } catch (UnknownHostException e) {
            System.out.println("No host : " + e);
        }
        catch (Exception e)
        {
            System.out.println("Unexpected exception : " + e);
        }
        return new String[]{fallBack3, fallBack4};
    }
}
