package live.smoothing.device.sensor.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SensorTest {

    private final Sensor sensor = Sensor.builder()
            .sensorRegisteredAt(LocalDateTime.now())
            .sensorType(new SensorType())
            .sensorTags(List.of())
            .build();

    @Test
    void getSensorRegisteredAt() {
        assertNotNull(sensor.getSensorRegisteredAt());
    }

    @Test
    void getSensorType() {
        assertNotNull(sensor.getSensorType());
    }

    @Test
    void getSensorTags() {
        assertNotNull(sensor.getSensorTags());
    }
}