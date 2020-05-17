package weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
    public String index() throws IOException {

        return weatherService.getTemperatureByLastDays(10).toString() + "\n" +
                weatherService.getCurrentTemperature().toString();
    }
}
