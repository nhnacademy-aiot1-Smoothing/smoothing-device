package live.smoothing.device.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import live.smoothing.device.sensor.dto.SensorErrorListResponse;
import live.smoothing.device.sensor.dto.SensorListResponse;
import live.smoothing.device.sensor.dto.SensorRegisterRequest;
import live.smoothing.device.sensor.dto.SensorTypeListResponse;
import live.smoothing.device.sensor.service.SensorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(SensorController.class)
class SensorControllerTest {

    @MockBean
    private SensorService sensorService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addSensor() throws Exception {
        Constructor<SensorRegisterRequest> constructor = SensorRegisterRequest.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        SensorRegisterRequest sensorRegisterRequest = constructor.newInstance();

        mockMvc.perform(post("/api/device/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sensorRegisterRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void getSensor() throws Exception {
        Integer brokerId = 1;

        SensorListResponse sensorListResponse = new SensorListResponse(List.of());

        when(sensorService.getSensors(eq(brokerId),any())).thenReturn(sensorListResponse);

        mockMvc.perform(get("/api/device/sensors/" + brokerId))
                .andExpect(status().isOk())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(sensorListResponse)));
    }

    @Test
    void updateSensor() throws Exception {
        int sensorId = 1;
        Constructor<SensorRegisterRequest> constructor = SensorRegisterRequest.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        SensorRegisterRequest sensorRegisterRequest = constructor.newInstance();

        mockMvc.perform(put("/api/device/sensors/" + sensorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sensorRegisterRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSensor() throws Exception {
        int sensorId = 1;

        mockMvc.perform(delete("/api/device/sensors/" + sensorId))
                .andExpect(status().isOk());
    }

    @Test
    void getErrors() throws Exception {
        SensorErrorListResponse sensorErrorListResponse = new SensorErrorListResponse(List.of());

        when(sensorService.getSensorErrors(any())).thenReturn(sensorErrorListResponse);

        mockMvc.perform(get("/api/device/sensors/errors"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(sensorErrorListResponse)));
    }

    @Test
    void deleteError() throws Exception {
        int sensorErrorId = 1;

        mockMvc.perform(delete("/api/device/sensors/errors/" + sensorErrorId))
                .andExpect(status().isOk());
    }

    @Test
    void getSensorTypes() throws Exception {
        SensorTypeListResponse sensorTypeListResponse = new SensorTypeListResponse(List.of());

        when(sensorService.getSensorTypes()).thenReturn(sensorTypeListResponse);

        mockMvc.perform(get("/api/device/sensors/types"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(objectMapper.writeValueAsBytes(sensorTypeListResponse)));
    }
}