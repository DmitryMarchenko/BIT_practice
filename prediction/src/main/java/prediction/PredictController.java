package prediction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
public class PredictController {

    @Autowired
    private PredictService predictService;

    @GetMapping("/predict")
    public String predictRate() throws IOException, ParseException {
        return predictService.predict().toString();
    }
}
