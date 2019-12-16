package app.controllers;

import app.services.PredictService;
import app.services.RBCService;
import app.services.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@RestController
public class PredictController {
    @Autowired
    private WeatherService weatherService;

    @Autowired
    private RBCService rbcService;

    @Autowired
    private PredictService predictService;

    @PostMapping("/predict")
    public String predictRate(Double temperature) throws JsonProcessingException, ParseException {
        ArrayList<Pair<String, Double>> dateTemperatureList = weatherService.getDateTemperatureByLastDays(30);
        ArrayList<Pair<String, Double>> dateRateList = rbcService.getDateRateByLastDays(30);
        ArrayList<Double> temperatureList = new ArrayList<>();
        ArrayList<Double> rateList = new ArrayList<>();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        int i = 0;
        int j = 0;
        while (i < dateTemperatureList.size() && j < dateRateList.size()) {
            Long tempTimestamp = ft.parse(dateTemperatureList.get(i).getFirst()).getTime();
            Long rateTimestamp = ft.parse(dateRateList.get(j).getFirst()).getTime();
            //System.out.println(dateTemperatureList.get(i).getKey() + " " + dateRateList.get(j).getKey());
            if (tempTimestamp < rateTimestamp) {
                ++i;
            } else if (tempTimestamp > rateTimestamp) {
                ++j;
            } else {
                //System.out.println(dateRateList.get(j).getKey());
                temperatureList.add(dateTemperatureList.get(i).getSecond());
                rateList.add(dateRateList.get(j).getSecond());
                ++i;
                ++j;
            }
        }
        if (!predictService.getLearn()) {
            predictService.fit(temperatureList, rateList);
            predictService.setLearn(true);
        }
        return predictService.predict(temperature).toString();
    }
}
