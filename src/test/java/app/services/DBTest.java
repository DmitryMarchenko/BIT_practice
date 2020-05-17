package app.services;

import app.repositories.RateRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class DBTest {
    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private RBCService rbcService;

    @Test
    public void dbSaveAndRead() {
        rbcService.saveRate("2019-12-12", 63.3);
        assertTrue(rbcService.getRateByDate("2019-12-12").isPresent());
        assertEquals(63.3, rbcService.getRateByDate("2019-12-12").get().getRate(), 0.0001);
    }
}
