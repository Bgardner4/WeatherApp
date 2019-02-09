package com.example.weatherapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


//API Key

public class WeatherActivity extends AppCompatActivity {
    private static final String LATITUDE = "Latitude";
    private static final String LONGITUDE = "Longitude";

    TextView txtSum;
    //adding forecastapi stuff

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        if(s.hasNext()) return s.next();
        else return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        txtSum = (TextView)findViewById(R.id.txtSummary);

        String lat = getIntent().getStringExtra(LATITUDE);
        String lng = getIntent().getStringExtra(LONGITUDE);
        new GetWeather().execute(lat, lng);

    }

    private class GetWeather extends AsyncTask<String, Void, String> {
        ProgressDialog dialog = new ProgressDialog(WeatherActivity.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.setMessage("Please wait....");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            String response = null;
            /*try{

                String lat = strings[0];
                String lng = strings[1];
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://api.darksky.net/forecast/6a0fca6bf01cd2eae94603d86dfc3a89/%s,%s",lat,lng);
                response = http.getHTTPData(url);
                return response;
            }
            catch (Exception ex)
            {
                System.out.println("Exception in GeocodeActivity, doInBackground");
            }
            return null;*/
            try {
                URL url = new URL("https://api.darksky.net/forecast/6a0fca6bf01cd2eae94603d86dfc3a89/" + strings[0] + "," + strings[1]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream in = new BufferedInputStream(conn.getInputStream());
                response = convertStreamToString(in);

            } catch(java.net.MalformedURLException ex){

            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s){
            try{

                Log.d("REACHED POSTEXECUTE", "got to this stage");
                JSONObject json = new JSONObject(s);

                //String summary = ((JSONArray)jsonObject.get("currently")).getJSONObject(0).getJSONObject("summary").toString();
                String summary = json.getJSONObject("currently").getString("summary");
                txtSum.setText(String.format("Current Weather Summary: %s", summary));
                //String lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                  //      .getJSONObject("location").get("lng").toString();


               // txtCoord.setText(String.format("Coordinates : %s / %s ",lat,lng));





                if(dialog.isShowing())
                    dialog.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
