package live.smoothing.device.sensor.repository;

import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.broker.entity.ProtocolType;
import live.smoothing.device.broker.repository.BrokerRepository;
import live.smoothing.device.broker.repository.ProtocolTypeRepository;
import live.smoothing.device.sensor.dto.SensorResponse;
import live.smoothing.device.sensor.entity.Sensor;
import live.smoothing.device.sensor.entity.SensorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("dev")
class SensorRepositoryTest {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SensorTypeRepository sensorTypeRepository;

    @Autowired
    private BrokerRepository brokerRepository;

    @Autowired
    private ProtocolTypeRepository protocolTypeRepository;

    private ProtocolType protocolType = new ProtocolType("testProtocolType");

    private Broker broker = Broker.builder()
            .brokerIp("testBrokerIp")
            .brokerPort(1234)
            .brokerName("testBroker")
            .protocolType(protocolType)
            .build();

    private SensorType sensorType = new SensorType("testSensorType");

    private Sensor sensor = Sensor.builder()
            .sensorName("testSensor")
            .sensorType(sensorType)
            .broker(broker)
            .build();

    @BeforeEach
    void setUp() {
        protocolTypeRepository.save(protocolType);
        brokerRepository.save(broker);
        sensorTypeRepository.save(sensorType);
    }

    @Test
    void save() {
        Sensor saved = sensorRepository.save(sensor);

        assertNotNull(saved.getSensorId());
    }

    @Test
    void findByBrokerBrokerId() {
        Sensor saved = sensorRepository.save(sensor);

        Page<SensorResponse> found = sensorRepository.findByBrokerBrokerId(broker.getBrokerId(), Pageable.ofSize(10));

        assertEquals(1, found.getTotalElements());
        assertEquals(saved.getSensorName(), found.getContent().get(0).getSensorName());
    }

    @Test
    void findSensorWithTopicBySensorId() {
        Sensor saved = sensorRepository.save(sensor);

        Optional<Sensor> found = sensorRepository.findSensorWithTopicBySensorId(saved.getSensorId());

        assertTrue(found.isPresent());
        assertTrue(found.get().getTopics().stream().anyMatch(topic -> topic.getSensor().getSensorId().equals(saved.getSensorId())));
    }
}