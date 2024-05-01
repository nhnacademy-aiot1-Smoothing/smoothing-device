package live.smoothing.device.broker.repository;

import live.smoothing.device.broker.dto.BrokerResponse;
import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.broker.entity.ProtocolType;
import live.smoothing.device.sensor.entity.Sensor;
import live.smoothing.device.sensor.entity.SensorType;
import live.smoothing.device.sensor.entity.Topic;
import live.smoothing.device.sensor.entity.TopicType;
import live.smoothing.device.sensor.repository.SensorRepository;
import live.smoothing.device.sensor.repository.SensorTypeRepository;
import live.smoothing.device.sensor.repository.TopicRepository;
import live.smoothing.device.sensor.repository.TopicTypeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("dev")
class BrokerRepositoryTest {

    @Autowired
    private BrokerRepository brokerRepository;

    @Autowired
    private ProtocolTypeRepository protocolTypeRepository;

    @Autowired
    private SensorTypeRepository sensorTypeRepository;

    @Autowired
    private TopicTypeRepository topicTypeRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private TopicRepository topicRepository;

    private ProtocolType protocolType = new ProtocolType("testProtocolType");

    private TopicType topicType = new TopicType("testTopicType");

    private SensorType sensorType = new SensorType("testSensorType");

    private Sensor sensor;

    private Topic topic;

    private Broker broker;

    @BeforeEach
    void setUp() {
        protocolTypeRepository.save(protocolType);
        topicTypeRepository.save(topicType);
        sensorTypeRepository.save(sensorType);
        broker = Broker.builder()
                .brokerName("testBroker")
                .brokerIp("testBrokerIp")
                .brokerPort(1234)
                .protocolType(protocolType)
                .build();
        sensor = Sensor.builder()
                .broker(broker)
                .sensorName("testSensor")
                .sensorRegisteredAt(LocalDateTime.now())
                .sensorType(sensorType)
                .build();
        topic = Topic.builder()
                .sensor(sensor)
                .topicType(topicType)
                .topic("testTopic")
                .build();
    }

    @AfterEach
    void tearDown() {
        brokerRepository.deleteAll();
        sensorRepository.deleteAll();
        topicRepository.deleteAll();
    }

    @Test
    void save(){
        Broker save = brokerRepository.save(broker);

        Assertions.assertNotNull(save);
        assertEquals(broker.getBrokerName(), save.getBrokerName());
    }

    @Test
    void getAllWith() {
        brokerRepository.save(broker);
        sensorRepository.save(sensor);
        topicRepository.save(topic);

        List<Broker> brokers = brokerRepository.getAllWith();
        Assertions.assertAll(
                ()-> assertEquals(1, brokers.size()),
                ()-> assertEquals(broker.getBrokerName(), brokers.get(0).getBrokerName())
        );
    }

    @Test
    void getBrokers() {
        brokerRepository.save(broker);
        Pageable pageable = Pageable.ofSize(10);
        Page<BrokerResponse> result = brokerRepository.getBrokers(pageable);

        assertEquals(1, result.getTotalElements());
    }
}