package app.weather.components;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {
    public String time;
    public Double temperatureHigh;
}
