package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.entity.SensorType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class SensorTypeRepositoryTest {

    @Autowired
    private SensorTypeRepository sensorTypeRepository;

    private SensorType sensorType = new SensorType("testSensorType");

    @Test
    void getAllSensorType() {
        sensorTypeRepository.save(sensorType);
        assertNotNull(sensorTypeRepository.getAllSensorType());
    }
}