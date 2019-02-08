package com.example.weatherapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpDataHandler {

    public HttpDataHandler(){}

    public String getHTTPData(String requestURL){
        URL url;
        String response = "";
        try{
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            Log.d("OPEN CONNECTION:", "HTTP URL connention opened");
            conn.setRequestMethod("GET");
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            Log.d("UPCOMING RESPONSE CODE", "see if up to here you get that error");
            int responseCode = conn.getResponseCode();
            Log.d("RESPONSE CODE:", "Response code written successfully");
            if(responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else{
                response = "";
            }
        } catch(ProtocolException e){
            e.printStackTrace();
        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        Log.d("HTTPDATA COMPLETE", "next line will request response");
        return response;
    }
}
