package com.example.weatherapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


//API Key

public class WeatherActivity extends AppCompatActivity {
    private static final String LATITUDE = "Latitude";
    private static final String LONGITUDE = "Longitude";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

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
            String response;
            try{
                String lat = strings[0];
                String lng = strings[1];
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://api.darksky.net/forecast/[API-KEY]/%s,%s",lat,lng);
                //TODO: add api key
                response = http.getHTTPData(url);
                return response;
            }
            catch (Exception ex)
            {
                System.out.println("Exception in WeatherActivity, doInBackground");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s){
            try{
                JSONObject jsonObject = new JSONObject(s);

                String lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lat").toString();
                String lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lng").toString();


                txtCoord.setText(String.format("Coordinates : %s / %s ",lat,lng));

                Intent mapsIntent = new Intent(GeoCodeActivity.this, MapsActivity.class);
                mapsIntent.putExtra(LATITUDE, lat);
                mapsIntent.putExtra(LONGITUDE, lng);
                startActivity(mapsIntent);



                if(dialog.isShowing())
                    dialog.dismiss();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
