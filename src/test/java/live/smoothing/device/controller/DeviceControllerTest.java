package live.smoothing.device.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.smoothing.device.broker.dto.RuleEngineResponse;
import live.smoothing.device.broker.service.BrokerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(DeviceController.class)
class DeviceControllerTest {

    @MockBean
    private BrokerService brokerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getInitialization() throws Exception {
        RuleEngineResponse ruleEngineResponse = new RuleEngineResponse(123, "brokerIp", 123, "protocolType", Set.of("topics"));

        when(brokerService.getInitBrokers()).thenReturn(List.of(ruleEngineResponse));

        mockMvc.perform(get("/api/device/initialization"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(List.of(ruleEngineResponse))));
    }
}