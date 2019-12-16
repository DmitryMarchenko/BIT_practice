package app.services;

import app.entity.Temperature;
import app.repositories.TemperatureRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {
    @Mock
    private TemperatureRepository temperatureRepository;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    public void getTemperatureByLastDays() throws JsonProcessingException {
        Optional<Temperature> optionalTemperature = Optional.of(new Temperature("2019-12-12", 2.5));
        Mockito.when(temperatureRepository.findByDate(anyString())).thenReturn(optionalTemperature);
        int lastDays = 10;
        ArrayList<Double> answer = new ArrayList<>();
        for (int i = 0; i < lastDays; ++i) {
            answer.add(2.5);
        }
        assertEquals(weatherService.getTemperatureByLastDays(lastDays), answer);
    }
}