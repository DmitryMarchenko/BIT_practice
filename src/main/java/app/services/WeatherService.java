package app.services;

import app.entity.Temperature;
import app.repositories.TemperatureRepository;
import app.weather.components.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Component
public class WeatherService {
    @Autowired
    private TemperatureRepository temperatureRepository;

    private String getResponse(Long time) {
        String key = "2e0f1c259d5bcd5ad09dbf2af5e76171";
        String url = "https://api.darksky.net/forecast/" + key + "/55.7,37.6," +
                time + "?units=auto&exclude=hourly,flags";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assert response.getStatusCode().equals(HttpStatus.OK);
        return response.getBody();
    }

    private WeatherResponse getWeatherResponse(Long time) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(getResponse(time), WeatherResponse.class);
    }

    public ArrayList<Pair<String, Double>> getDateTemperatureByLastDays(Integer lastDays) throws JsonProcessingException {
        ArrayList<Pair<String, Double>> dateTemperatureByLastDays = new ArrayList<>();
        long time = System.currentTimeMillis() / 1000L;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        for (long i = lastDays - 1; i >= 0; i--) {
            Date date = new Date((time - i * 86400L) * 1000L);
            String stringDate = ft.format(date);
            Double temperature = 0.;
            Optional<Temperature> optionalTemperature = getTemperatureByDate(stringDate);
            if (optionalTemperature.isPresent()) {
                temperature = optionalTemperature.get().getTemperature();
            } else {
                WeatherResponse weatherResponse = getWeatherResponse(time - i * 86400L);
                temperature = weatherResponse.daily.data.get(0).temperatureHigh;
                saveTemperature(stringDate, temperature);
            }
            dateTemperatureByLastDays.add(Pair.of(stringDate, temperature));
        }
        return dateTemperatureByLastDays;
    }

    public ArrayList<Double> getTemperatureByLastDays(Integer lastDays) throws JsonProcessingException {
        ArrayList<Pair<String, Double>> dateTemperatureByLastDays = getDateTemperatureByLastDays(lastDays);
        ArrayList<Double> temperatureByLastDays = new ArrayList<>();
        for (Pair<String, Double> pair : dateTemperatureByLastDays){
            temperatureByLastDays.add(pair.getSecond());
        }
        return temperatureByLastDays;
    }

    public Double getCurrentTemperature() throws JsonProcessingException {
        WeatherResponse weatherResponse = getWeatherResponse(System.currentTimeMillis() / 1000L);
        return weatherResponse.currently.temperature;
    }

    @Transactional
    private void saveTemperature(String date, Double temperature) {
        temperatureRepository.save(new Temperature(date, temperature));
    }

    @Transactional
    private Optional<Temperature> getTemperatureByDate(String date) {
        return temperatureRepository.findByDate(date);
    }
}
