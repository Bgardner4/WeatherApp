package com.example.weatherapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static int TIME_OUT = 5000; //Time to launch the another activity

    private GoogleMap mMap;
    private static final String LATITUDE = "Latitude";
    private static final String LONGITUDE = "Longitude";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String lat = getIntent().getStringExtra(LATITUDE);
                String lng = getIntent().getStringExtra(LONGITUDE);
                Intent weatherIntent = new Intent(MapsActivity.this, WeatherActivity.class);
                weatherIntent.putExtra(LATITUDE, lat);
                weatherIntent.putExtra(LONGITUDE, lng);
                startActivity(weatherIntent);
                finish();
            }
        }, TIME_OUT);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String lat = getIntent().getStringExtra(LATITUDE);
        String lng = getIntent().getStringExtra(LONGITUDE);


        // Add a marker in Sydney and move the camera
        LatLng address = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        mMap.addMarker(new MarkerOptions().position(address).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(address));
    }
}
