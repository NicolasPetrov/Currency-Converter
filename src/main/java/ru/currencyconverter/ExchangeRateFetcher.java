package ru.currencyconverter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ExchangeRateFetcher {
    private static final String API_URL = "http://api.currencylayer.com/live";
    private static final String ACCESS_KEY = "YOUR_ACCESS_KEY";

    public static double getExchangeRate(String from, String to) {
        try {
            String urlStr = API_URL + "?access_key=" + ACCESS_KEY + "&currencies=" + to + "&source=" + from + "&format=1";
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(content.toString());
            if (json.getBoolean("success")) {
                String rateKey = from + to;
                double exchangeRate = json.getJSONObject("quotes").getDouble(rateKey);
                return exchangeRate;
            } else {
                System.err.println("Error fetching exchange rate: " + json.getString("error"));
                return 1.0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1.0;
        }
    }
}