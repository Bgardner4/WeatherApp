package com.example.weatherapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GeoCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_code);
    }

    private class GetCoordinates extends AsyncTask<String,Void,String> {

        ProgressDialog dialog = new ProgressDialog(GeoCodeActivity.this);

        @Override
        protected void onPreExecute(){}

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s){}
    }
}
