package live.smoothing.device.broker.repository;

import live.smoothing.device.broker.dto.BrokerErrorResponse;
import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.broker.entity.BrokerErrorLog;
import live.smoothing.device.broker.entity.ProtocolType;
import live.smoothing.device.sensor.entity.*;
import live.smoothing.device.sensor.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class BrokerErrorLogRepositoryTest {
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

    @Autowired
    private BrokerErrorLogRepository brokerErrorLogRepository;

    private ProtocolType protocolType = new ProtocolType("testProtocolType");

    private TopicType topicType = new TopicType("testTopicType");

    private SensorType sensorType = new SensorType("testSensorType");

    private Sensor sensor;

    private Topic topic;

    private Broker broker;

    private BrokerErrorLog brokerErrorLog;


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
                .topics(null)
                .sensorType(sensorType)
                .build();
        topic = Topic.builder()
                .sensor(sensor)
                .topicType(topicType)
                .topic("testTopic")
                .build();
        brokerErrorLog = BrokerErrorLog.builder()
                .broker(broker)
                .brokerErrorCreatedAt(LocalDateTime.now())
                .brokerErrorType("testBrokerErrorType")
                .brokerErrorSolvedAt(LocalDateTime.now())
                .build();
        brokerRepository.save(broker);
        sensorRepository.save(sensor);
        topicRepository.save(topic);
    }

    @Test
    void save(){
        BrokerErrorLog result = brokerErrorLogRepository.save(brokerErrorLog);

        assertNotNull(result);
    }

    @Test
    void getAllErrors() {
        brokerErrorLogRepository.save(brokerErrorLog);

        Page<BrokerErrorResponse> result = brokerErrorLogRepository.getAllErrors(Pageable.ofSize(10));

        assertEquals(1, result.getTotalElements());
    }
}