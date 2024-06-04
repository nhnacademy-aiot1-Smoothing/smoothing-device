package live.smoothing.device.sensor.repository;

import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.broker.entity.ProtocolType;
import live.smoothing.device.broker.repository.BrokerRepository;
import live.smoothing.device.broker.repository.ProtocolTypeRepository;
import live.smoothing.device.sensor.dto.SensorErrorResponse;
import live.smoothing.device.sensor.entity.*;
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
class SensorErrorLogRepositoryTest {
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
    private SensorErrorLogRepository sensorErrorLogRepository;

    private ProtocolType protocolType = new ProtocolType("testProtocolType");

    private TopicType topicType = new TopicType("testTopicType");

    private SensorType sensorType = new SensorType("testSensorType");

    private Sensor sensor;

    private Topic topic;

    private Broker broker;

    private SensorErrorLog sensorErrorLog;

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
        sensorErrorLog = SensorErrorLog.builder()
                .sensorErrorType("testSensorErrorType")
                .sensorErrorCreatedAt(LocalDateTime.now())
                .sensorErrorValue(1.0)
                .broker(broker)
                .topic(topic)
                .build();
        brokerRepository.save(broker);
        sensorRepository.save(sensor);
        topicRepository.save(topic);
    }

    @Test
    void save(){
        SensorErrorLog result = sensorErrorLogRepository.save(sensorErrorLog);

        assertNotNull(result);
    }

    @Test
    void findAllSensorErrorLogs() {
        sensorErrorLogRepository.save(sensorErrorLog);
        Page<SensorErrorResponse> result = sensorErrorLogRepository.findAllSensorErrorLogs(Pageable.ofSize(10));

        assertEquals(1, result.getTotalElements());
    }
}