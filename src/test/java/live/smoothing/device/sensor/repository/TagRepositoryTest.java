package live.smoothing.device.sensor.repository;

import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.broker.entity.ProtocolType;
import live.smoothing.device.broker.repository.BrokerRepository;
import live.smoothing.device.sensor.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("dev")
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private SensorTagRepository sensorTagRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SensorTypeRepository sensorTypeRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicTypeRepository topicTypeRepository;

    @Autowired
    private BrokerRepository brokerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private TopicType topicType = new TopicType("testTopicType");

    private SensorType sensorType = new SensorType("testSensorType");

    private Tag tag = Tag.builder()
            .tagName("testTagName")
            .userId("testUserId")
            .build();

    private Sensor sensor = Sensor.builder()
            .sensorName("testSensorName")
            .sensorType(sensorType)
            .build();

    private SensorTag sensorTag = SensorTag.builder()
            .tag(tag)
            .sensor(sensor)
            .build();

    private Broker broker = Broker.builder()
            .brokerName("testBrokerName")
            .protocolType(new ProtocolType("testProtocolType"))
            .brokerIp("testBrokerIp")
            .brokerPort(1234)
            .build();

    private Topic topic = Topic.builder()
            .topic("testTopic")
            .topicType(topicType)
            .sensor(sensor)
            .build();


    @BeforeEach
    void setUp() {
        topicTypeRepository.save(topicType);
        sensorTypeRepository.save(sensorType);
        tagRepository.save(tag);
        sensorRepository.save(sensor);
        sensorTagRepository.save(sensorTag);
        brokerRepository.save(broker);
        topicRepository.save(topic);
    }

    @Test
    void getTopicsByUserIdAndTags() {
        entityManager.clear();

        List<String> topics = tagRepository.getTopicsByUserIdAndTags("testUserId", List.of("testTagName"), 1L, "testTopicType");
        assertEquals(1, topics.size());
        assertEquals("testTopic", topics.get(0));
    }
}