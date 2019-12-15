package app.weather.components;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDaily {
    public ArrayList<WeatherData> data;
}
