package app.services;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PredictService {
    private Double w0 = 0.;
    private Double w1 = 0.;

    public void setLearn(Boolean learn) {
        this.learn = learn;
    }

    public Boolean getLearn() {
        return learn;
    }

    private Boolean learn = false;

    private Double mean(List<Double> list) {
        Double sum = 0.;
        for (Double value : list) {
            sum += value;
        }
        return sum / list.size();
    }

    public void fit(List<Double> temperatureList, List<Double> ratesList) {
        Double a =  0.;
        Double b = 0.;
        for (int i = 0; i < temperatureList.size() && i < ratesList.size(); i++) {
            a += (temperatureList.get(i) - mean(temperatureList)) * (ratesList.get(i) - mean(ratesList));
            b += Math.pow(temperatureList.get(i) - mean(temperatureList), 2);
        }
        w1 = (b < 0.00000001)? 99999.0: a / b;
        w0 = mean(ratesList) - w1 * mean(temperatureList);
    }

    public Double predict(Double temperature) {
        return Math.max(w1 * temperature + w0, 0);
    }
}
