package app.controllers;

import app.RBCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class RBCController {
    @Autowired
    private RBCService rbcService;

    @RequestMapping("/rbc")
    public String index() throws ParseException {
        return rbcService.getRatesByLastDays(10).toString();
    }
}
