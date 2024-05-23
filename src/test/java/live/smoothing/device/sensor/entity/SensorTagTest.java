package live.smoothing.device.sensor.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorTagTest {

    private final SensorTag sensorTag = SensorTag.builder()
            .sensor(new Sensor())
            .tag(new Tag())
            .build();

    @Test
    void getSensor() {
        assertNotNull(sensorTag.getSensor());
    }
}