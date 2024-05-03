package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.entity.Sensor;
import live.smoothing.device.sensor.entity.SensorTag;
import live.smoothing.device.sensor.entity.SensorType;
import live.smoothing.device.sensor.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("dev")
class SensorTagRepositoryTest {

    @Autowired
    private SensorTagRepository sensorTagRepository;

    private SensorTag sensorTag = SensorTag.builder()
            .tag(Tag.builder()
                    .tagName("testTagName")
                    .userId("testUserId")
                    .build())
            .sensor(Sensor.builder()
                    .sensorName("testSensorName")
                    .sensorType(new SensorType("testSensorType"))
                    .build())
            .build();

    @Test
    void save(){
        SensorTag savedSensorTag = sensorTagRepository.save(sensorTag);
        assertNotNull(savedSensorTag);
    }

    @Test
    void findById() {
        SensorTag savedSensorTag = sensorTagRepository.save(sensorTag);
        Optional<SensorTag> foundSensorTag = sensorTagRepository.findById(savedSensorTag.getSensorTagId());

        assertTrue(foundSensorTag.isPresent());
    }

}