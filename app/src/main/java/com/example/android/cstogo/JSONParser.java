package com.example.android.cstogo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * - Yuro - 1.4.2015.
 */
public class JSONParser {

    final String TAG = "JsonParser.java";

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    public JSONObject getJSONFromUrl(String url) {
        HttpURLConnection conn;
        // make HTTP request
        try {

            URL setUrl = new URL(url);
            conn = (HttpURLConnection) setUrl.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();

            try {
                is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line;// = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");//line + "n");
                }
                is.close();
                json = sb.toString();

            } catch (Exception e) {
                Log.e(TAG, "Error converting result " + e.toString());
                return null;
            } finally {
                conn.disconnect();
            }

            // try parse the string to a JSON object
            try {
                jObj = new JSONObject(json);
            } catch (JSONException e) {
                Log.e(TAG, "Error parsing data " + e.toString());
                return null;
            }

        } catch (IOException e) {
            Log.d(TAG, "HTTP ERROR");
            e.printStackTrace();

            return null;
        }

        // return JSON String
        return jObj;
    }
}
