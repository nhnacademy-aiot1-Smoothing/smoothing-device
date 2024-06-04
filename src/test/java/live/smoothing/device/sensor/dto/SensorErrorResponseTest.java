package live.smoothing.device.sensor.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorErrorResponseTest {

    @Test
    void null_createdAt() {
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse(1, "error", "broker", null, 1.0, "topic");

        assertNotNull(sensorErrorResponse);
    }

}