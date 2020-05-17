package weather.components;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    public WeatherCurrently currently;
    public WeatherDaily daily;
}
