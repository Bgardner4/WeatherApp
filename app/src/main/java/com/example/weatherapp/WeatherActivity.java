package com.example.weatherapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class WeatherActivity extends AppCompatActivity {
    //Rabia driving
    private static final String LATITUDE = "Latitude";
    private static final String LONGITUDE = "Longitude";

    TextView txtSum;
    TextView txtPreProb;
    TextView txtPreType;
    TextView txtTemp;
    TextView txtAppTemp;
    TextView txtHumidity;
    TextView txtCloudCov;
    TextView txtWindSpeed;
    TextView txtUVIndex;
    //End of Rabia driving, Blake driving now
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        if(s.hasNext()) return s.next();
        else return "";
    }
    //End of Blake driving, Rabia driving now
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        txtSum = findViewById(R.id.txtSummary);
        txtPreProb = findViewById(R.id.precipProb);
        //txtPreType = findViewById(R.id.precipType);
        txtTemp = findViewById(R.id.txtTemperature);
        txtAppTemp = findViewById(R.id.txtApparentTemp);
        txtHumidity = findViewById(R.id.txtHumidity);
        txtCloudCov = findViewById(R.id.cloudCover);
        txtWindSpeed = findViewById(R.id.windSpeed);
        txtUVIndex = findViewById(R.id.uvIndex);


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
        //End of Rabia driving, Blake driving now
        @Override
        protected String doInBackground(String... strings) {

            String response = "";
            try{
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://api.darksky.net/forecast/6a0fca6bf01cd2eae94603d86dfc3a89/%s,%s", strings[0], strings[1]);
                response = http.getHTTPData(url);
                return response;
            }
            catch (Exception ex)
            {
                System.out.println("Exception in WeatherActivity, doInBackground");
            }
            return response;
        }
        //End of Blake driving, Rabia driving now
        @Override
        protected void onPostExecute(String s){
            try{

                Log.d("REACHED POSTEXECUTE", "got to this stage");
                JSONObject json = new JSONObject(s);

                String summary = json.getJSONObject("currently").getString("summary");
                String precipProb = json.getJSONObject("currently").getString("precipProbability");
                //String precipType = json.getJSONObject("currently").getString("precipType");          //will cause weatherActivity to keep loading and not show results
                String temp = json.getJSONObject("currently").getString("temperature");
                String apparentTemp = json.getJSONObject("currently").getString("apparentTemperature");
                String humidity = json.getJSONObject("currently").getString("humidity");
                String cloudCover = json.getJSONObject("currently").getString("cloudCover");
                String windSpeed = json.getJSONObject("currently").getString("windSpeed");
                String uvIndex = json.getJSONObject("currently").getString("uvIndex");

                txtSum.setText(String.format("Current Weather Summary: %s", summary));
                txtPreProb.setText(String.format("Precipitation Probability: %s", precipProb));
                //txtPreType.setText(String.format("Precipitation Type: %s", precipType));
                txtTemp.setText(String.format("Temperature: %s", temp));
                txtAppTemp.setText(String.format("Apparent Temperature: %s", apparentTemp));
                txtHumidity.setText(String.format("Humidity: %s", humidity));
                txtCloudCov.setText(String.format("Cloud Cover: %s", cloudCover));
                txtWindSpeed.setText(String.format("Wind Speed: %s", windSpeed));
                txtUVIndex.setText(String.format("UV Index: %s", uvIndex));


                if(dialog.isShowing())
                    dialog.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
