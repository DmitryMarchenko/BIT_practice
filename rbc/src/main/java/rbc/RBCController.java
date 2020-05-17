package rbc;

import rbc.RBCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class RBCController {
    @Autowired
    private RBCService rbcService;

    @GetMapping("/rbc")
    public String index() throws ParseException {
        return rbcService.getRatesByLastDays(10).toString();
    }
}
