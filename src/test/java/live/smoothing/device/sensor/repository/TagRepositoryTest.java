package live.smoothing.device.sensor.repository;

import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.broker.entity.ProtocolType;
import live.smoothing.device.broker.repository.BrokerRepository;
import live.smoothing.device.sensor.dto.SensorTopicDto;
import live.smoothing.device.sensor.dto.TagResponse;
import live.smoothing.device.sensor.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
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

    private final TopicType topicType = new TopicType("testTopicType");

    private final SensorType sensorType = new SensorType("testSensorType");

    private final Tag tag = Tag.builder()
            .tagName("testTagName")
            .userId("testUserId")
            .build();

    private final Sensor sensor = Sensor.builder()
            .sensorName("testSensorName")
            .sensorType(sensorType)
            .build();

    private SensorTag sensorTag = SensorTag.builder()
            .tag(tag)
            .sensor(sensor)
            .build();

    private final Broker broker = Broker.builder()
            .brokerName("testBrokerName")
            .protocolType(new ProtocolType("testProtocolType"))
            .brokerIp("testBrokerIp")
            .brokerPort(1234)
            .build();

    private final Topic topic = Topic.builder()
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
        entityManager.clear();
    }

    @Test
    void existsByUserIdAndTagName() {
        assertTrue(tagRepository.existsByUserIdAndTagName("testUserId", "testTagName"));
    }

    @Test
    void getByUserId() {
        List<TagResponse> tags = tagRepository.getByUserId("testUserId");
        assertEquals(1, tags.size());
        assertEquals("testTagName", ReflectionTestUtils.getField(tags.get(0), "tagName"));
    }

}