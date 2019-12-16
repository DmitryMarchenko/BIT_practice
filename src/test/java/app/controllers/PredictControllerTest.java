package app.controllers;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PredictControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void predictRateSign() throws Exception {
        MvcResult result = mockMvc.perform(post("/predict").param("temperature", "10"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(Double.parseDouble(result.getResponse().getContentAsString()) > 0);
    }

    @Test
    public void predictRateConnectionTest() throws Exception {
        mockMvc.perform(post("/predict").param("temperature", "10"))
                .andDo(print()).andExpect(status().isOk());
    }
}