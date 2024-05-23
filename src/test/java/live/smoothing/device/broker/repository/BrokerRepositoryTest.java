package live.smoothing.device.broker.repository;

import live.smoothing.device.broker.dto.BrokerResponse;
import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.broker.entity.ProtocolType;
import live.smoothing.device.sensor.entity.Sensor;
import live.smoothing.device.sensor.entity.SensorType;
import live.smoothing.device.sensor.entity.Topic;
import live.smoothing.device.sensor.entity.TopicType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class BrokerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BrokerRepository brokerRepository;

    private final ProtocolType protocolType = new ProtocolType("testProtocolType");

    private final TopicType topicType = new TopicType("testTopicType");

    private final SensorType sensorType = new SensorType("testSensorType");

    private final Broker broker = Broker.builder()
            .brokerName("testBroker")
            .brokerIp("testBrokerIp")
            .brokerPort(1234)
            .protocolType(protocolType)
            .build();

    private final Sensor sensor = Sensor.builder()
            .broker(broker)
            .sensorName("testSensor")
            .sensorRegisteredAt(LocalDateTime.now())
            .sensorType(sensorType)
            .build();

    private final Topic topic = Topic.builder()
            .sensor(sensor)
            .topicType(topicType)
            .topic("testTopic")
            .build();

    @BeforeEach
    void setUp() {
        entityManager.persist(protocolType);
        entityManager.persist(topicType);
        entityManager.persist(sensorType);
        entityManager.persist(broker);
        entityManager.persist(sensor);
        entityManager.persist(topic);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void getAllWith() {

        List<Broker> brokers = brokerRepository.getAllWith();
        System.out.println(brokers.get(0).getSensors());
        System.out.println(brokers.get(0).getBrokerId());
        Assertions.assertAll(
                () -> assertEquals(1, brokers.size()),
                () -> assertEquals(broker.getBrokerName(), brokers.get(0).getBrokerName()),
                () -> assertTrue(brokers.stream().anyMatch(broker -> broker.getSensors().stream().anyMatch(sensor -> sensor.getSensorName().equals(sensor.getSensorName()))))
        );
    }

    @Test
    void getBrokers() {
        Pageable pageable = Pageable.ofSize(10);
        Page<BrokerResponse> result = brokerRepository.getBrokers(pageable);

        assertEquals(1, result.getTotalElements());
    }
}