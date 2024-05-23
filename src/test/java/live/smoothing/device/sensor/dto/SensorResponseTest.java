package live.smoothing.device.sensor.dto;

import live.smoothing.device.sensor.entity.SensorType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SensorResponseTest {

    @Test
    void Notnull_registeredAt() {
        SensorResponse sensorResponse = new SensorResponse(1, new SensorType(""), "topic", LocalDateTime.now());

        assertNotNull(sensorResponse);
    }

}