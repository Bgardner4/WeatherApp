package com.example.weatherapp;

import android.util.Log;

import tk.plogitech.darksky.forecast.APIKey;
import tk.plogitech.darksky.forecast.DarkSkyClient;
import tk.plogitech.darksky.forecast.ForecastException;
import tk.plogitech.darksky.forecast.ForecastRequest;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder;
import tk.plogitech.darksky.forecast.GeoCoordinates;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;

public class WeatherRequestHandler{
    ForecastRequest request = new ForecastRequestBuilder()
            .key(new APIKey("your-private-key"))
            .location(new GeoCoordinates(new Longitude(13.377704), new Latitude(52.516275))).build();

    DarkSkyClient client = new DarkSkyClient();

    String forecast = client.forecastJsonString(request);


    public WeatherRequestHandler() throws ForecastException {
    }
}
