package app.services;

import app.entity.Rate;
import app.repositories.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Component
public class RBCService {
    @Autowired
    private RateRepository rateRepository;

    public String getResponse(String date) {
        String[] yMd = date.split("-");
        String url = "http://export.rbc.ru/free/selt.0/free.fcgi?period=DAILY&tickers=USD000000TOD" +
                "&separator=TAB&data_format=BROWSER&d1=" + yMd[2] + "&m1=" + yMd[1] + "&y1=" + yMd[0];
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assert responseEntity.getStatusCode().equals(HttpStatus.OK);
        return responseEntity.getBody();
    }

    public ArrayList<Pair<String, Double>> getRBCResponse(String date) {
        ArrayList<Pair<String, Double>> parsedData = new ArrayList<>();
        String response = getResponse(date);
        if (response == null) {
            return parsedData;
        }
        String[] rows = response.split("\n");
        for (String row : rows) {
            String[] items = row.split("\t");
            String stringDate = items[1];
            String stringRate = items[items.length - 1];
            parsedData.add(Pair.of(stringDate, Double.parseDouble(stringRate)));
        }
        return parsedData;
    }

    private Double extractAndSave(ArrayList<Pair<String, Double>> dateRateList, String date) {
        Double result = 63.;
        for (Pair<String, Double> dateRatePair : dateRateList) {
            String currentDate = dateRatePair.getFirst();
            Double currentRate = dateRatePair.getSecond();
            if (currentDate.equals(date)) {
                result = currentRate;
            }
            Optional<Rate> optionalRate = getRateByDate(currentDate);
            if (!optionalRate.isPresent()) {
                saveRate(currentDate, currentRate);
            }
        }
        return result;
    }

    public ArrayList<Pair<String, Double>> getDateRateByLastDays(Integer lastDays) throws ParseException {
        ArrayList<Pair<String, Double>> dateRateByLastDays = new ArrayList<>();
        long time = System.currentTimeMillis() / 1000L;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        for (long i = lastDays - 1; i >= 0; i--) {
            Date date = new Date((time - i * 86400L) * 1000L);
            String stringDate = ft.format(date);
            Double rate = 0.;
            Optional<Rate> optionalRate = getRateByDate(stringDate);
            if (optionalRate.isPresent()) {
                rate = optionalRate.get().getRate();
            } else {
                ArrayList<Pair<String, Double>> RBCResponse = getRBCResponse(stringDate);
                rate = extractAndSave(RBCResponse, stringDate);
            }
            dateRateByLastDays.add(Pair.of(stringDate, rate));
        }
        return dateRateByLastDays;
    }

    public ArrayList<Double> getRatesByLastDays(Integer lastDays) throws ParseException {
        ArrayList<Pair<String, Double>> dateRatesPairs = getDateRateByLastDays(lastDays);
        ArrayList<Double> ratesByLastDays = new ArrayList<>();
        for (Pair<String, Double> pair : dateRatesPairs){
            ratesByLastDays.add(pair.getSecond());
        }
        return ratesByLastDays;
    }

    @Transactional
    private void saveRate(String date, Double rate) {
        rateRepository.save(new Rate(date, rate));
    }

    @Transactional
    private Optional<Rate> getRateByDate(String date) {
        return rateRepository.findByDate(date);
    }
}
