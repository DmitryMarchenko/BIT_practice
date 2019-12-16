package app.services;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class RBCServiceTest {
    @Test
    public void getRBCResponse() {
        RBCService rbcService = new RBCService();
        RBCService rbcServiceMock = Mockito.spy(rbcService);
        String date = "2019-12-12";
        String response = "USD000000TOD\t2019-12-12\t63.28\t63.31\t62.88\t62.92\t691152000\t63.1479\n" +
                "USD000000TOD\t2019-12-13\t62.67\t62.9275\t62.095\t62.73\t951370000\t62.4911";
        ArrayList<Pair<String, Double>> answer = new ArrayList<>(2);
        answer.add(Pair.of("2019-12-12", 63.1479));
        answer.add(Pair.of("2019-12-13", 62.4911));
        Mockito.when(rbcServiceMock.getResponse(date)).thenReturn(response);
        assertEquals(rbcServiceMock.getRBCResponse(date), answer);
    }
}