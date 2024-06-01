package live.smoothing.device.sensor.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SensorErrorLogTest {

    private final SensorErrorLog sensorErrorLog = SensorErrorLog.builder()
            .sensorErrorType("error")
            .sensorErrorCreatedAt(LocalDateTime.now())
            .sensorErrorValue(1.0)
            .sensor(new Sensor())
            .topic(new Topic())
            .build();

    @Test
    void getSensorErrorType() {
        assertEquals("error", sensorErrorLog.getSensorErrorType());
    }

    @Test
    void getSensorErrorCreatedAt() {
        assertNotNull(sensorErrorLog.getSensorErrorCreatedAt());
    }

    @Test
    void getSensorErrorValue() {
        assertEquals(1.0, sensorErrorLog.getSensorErrorValue());
    }

    @Test
    void getSensor() {
        assertNotNull(sensorErrorLog.getSensor());
    }

    @Test
    void getTopic() {
        assertNotNull(sensorErrorLog.getTopic());
    }
}