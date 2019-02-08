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




//API Key

public class WeatherActivity extends AppCompatActivity {
    private static final String LATITUDE = "Latitude";
    private static final String LONGITUDE = "Longitude";

    TextView txtSum;
    //adding forecastapi stuff

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        txtSum = (TextView)findViewById(R.id.txtSummary);

        String lat = getIntent().getStringExtra(LATITUDE);
        String lng = getIntent().getStringExtra(LONGITUDE);
        new GetWeather().execute(lat, lng);

        //ForecastApi.create("6a0fca6bf01cd2eae94603d86dfc3a89");

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
                String url = String.format("https://api.darksky.net/forecast/6a0fca6bf01cd2eae94603d86dfc3a89/%s,%s",lat,lng);
                response = http.getHTTPData(url);
                return response;
            }
            catch (Exception ex)
            {
                System.out.println("Exception in GeocodeActivity, doInBackground");
            }
            return null;
             /*
                ForecastRequest request = new ForecastRequestBuilder()
                        .key(new APIKey("6a0fca6bf01cd2eae94603d86dfc3a89"))
                        .location(new GeoCoordinates(new Longitude(Double.parseDouble(lng)), new Latitude(Double.parseDouble(lat)))).build();

                DarkSkyClient client = new DarkSkyClient();

                try {
                    forecast = client.forecastJsonString(request);
                    return forecast;
                } catch (ForecastException e) {
                    e.printStackTrace();
                }

*/



        }

        @Override
        protected void onPostExecute(String s){
            try{

                Log.d("REACHED POSTEXECUTE", "got to this stage");
                JSONObject jsonObject = new JSONObject(s);

                String summary = ((JSONArray)jsonObject.get("currently")).getJSONObject(0).getJSONObject("summary").toString();
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
