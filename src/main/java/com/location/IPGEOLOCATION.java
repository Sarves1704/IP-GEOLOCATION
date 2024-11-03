package com.location;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner; // Import Scanner
import org.json.JSONObject;

public class IPGEOLOCATION {
    private static final String API_URL = "http://ipinfo.io/";
    private static final String API_TOKEN = "89f5ff0abac7bf"; // Replace with your actual API token

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the IP address to track: ");
        String ipAddress = scanner.nextLine(); // Get IP address from user
        scanner.close();

        trackLocation(ipAddress);
    }

    public static void trackLocation(String ipAddress) {
        try {
            String urlStr = API_URL + ipAddress + "?token=" + API_TOKEN;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                String country = jsonResponse.optString("country");
                String region = jsonResponse.optString("region");
                String city = jsonResponse.optString("city");

                System.out.println("IP Address: " + ipAddress);
                System.out.println("Country: " + country);
                System.out.println("Region: " + region);
                System.out.println("City: " + city);
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
