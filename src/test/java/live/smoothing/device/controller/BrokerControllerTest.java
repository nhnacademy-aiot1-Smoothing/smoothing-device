package live.smoothing.device.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.smoothing.device.broker.dto.BrokerAddRequest;
import live.smoothing.device.broker.dto.BrokerErrorListResponse;
import live.smoothing.device.broker.dto.BrokerListResponse;
import live.smoothing.device.broker.dto.BrokerUpdateRequest;
import live.smoothing.device.broker.service.BrokerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BrokerController.class)
class BrokerControllerTest {

    @MockBean
    private BrokerService brokerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addBroker() throws Exception {
        BrokerAddRequest brokerAddRequest = new BrokerAddRequest();

        mockMvc.perform(post("/api/device/brokers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brokerAddRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void getBrokers() throws Exception {
        BrokerListResponse brokerListResponse = new BrokerListResponse(List.of(), 1);

        when(brokerService.getBrokers(any())).thenReturn(brokerListResponse);

        mockMvc.perform(get("/api/device/brokers"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(brokerListResponse)));
    }

    @Test
    void updateBroker() throws Exception {
        Integer brokerId = 1;
        BrokerUpdateRequest brokerUpdateRequest = new BrokerUpdateRequest();

        mockMvc.perform(put("/api/device/brokers/" + brokerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brokerUpdateRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBroker() throws Exception {
        Integer brokerId = 1;

        mockMvc.perform(delete("/api/device/brokers/" + brokerId))
                .andExpect(status().isOk());
    }

    @Test
    void getErrors() throws Exception {
        BrokerErrorListResponse brokerErrorListResponse = new BrokerErrorListResponse(List.of());

        when(brokerService.getErrors(any())).thenReturn(brokerErrorListResponse);

        mockMvc.perform(get("/api/device/brokers/errors"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(brokerErrorListResponse)));
    }

    @Test
    void deleteError() throws Exception {
        Integer brokerErrorId = 1;

        mockMvc.perform(delete("/api/device/brokers/errors/" + brokerErrorId))
                .andExpect(status().isOk());
    }
}