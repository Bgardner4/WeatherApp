package com.example.weatherapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GeoCodeActivity extends AppCompatActivity {

    Button btnShowCoord;
    EditText edtAddress;
    TextView txtCoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_code);
    }

    private class GetCoordinates extends AsyncTask<String,Void,String> {

        ProgressDialog dialog = new ProgressDialog(GeoCodeActivity.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String response;
            try{
                String address  = strings[0];
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s", address);
                response = http.getHTTPData(url);
                return response;
            } catch(Exception ex){

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

               if(dialog.isShowing())
                   dialog.dismiss();

           } catch(JSONException e){
               e.printStackTrace();
           }
        }
    }
}
