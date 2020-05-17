package prediction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PredictService {

    public static void main(String[] args) {
        SpringApplication.run(PredictService.class, args);
    }

    public String predict() {
        RestTemplate restTemplate = new RestTemplate();

        String rbc_url = "http://rbc:8081/rbc";
        String rates = restTemplate.getForEntity(rbc_url, String.class).getBody();

        String weather_url = "http://weather:8083/weather";
        String weather = restTemplate.getForEntity(weather_url, String.class).getBody();

        return "Rates: " + rates + '\n' + "Weather: " + weather + '\n';
    }
}
