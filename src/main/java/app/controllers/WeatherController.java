package app.controllers;

import app.services.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
    public String index() throws JsonProcessingException {

        return weatherService.getTemperatureByLastDays(10).toString() + "\n" +
                weatherService.getCurrentTemperature().toString();
    }
}
