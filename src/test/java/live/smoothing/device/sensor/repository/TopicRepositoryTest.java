package live.smoothing.device.sensor.repository;

import live.smoothing.device.broker.entity.Broker;
import live.smoothing.device.broker.entity.ProtocolType;
import live.smoothing.device.sensor.dto.TopicResponse;
import live.smoothing.device.sensor.entity.Sensor;
import live.smoothing.device.sensor.entity.SensorType;
import live.smoothing.device.sensor.entity.Topic;
import live.smoothing.device.sensor.entity.TopicType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TopicRepositoryTest {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicTypeRepository topicTypeRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Sensor sensor = Sensor.builder()
            .sensorName("testSensorName")
            .sensorType(new SensorType("testSensorType"))
            .broker(Broker.builder()
                    .protocolType(new ProtocolType("testProtocolType"))
                    .brokerName("testBrokerName")
                    .brokerIp("testBrokerIp")
                    .brokerPort(1234)
                    .build()
            )
            .build();

    private TopicType topicType = new TopicType("testTopicType");

    private Topic topic1 = Topic.builder()
            .topic("testTopic1")
            .topicType(topicType)
            .sensor(sensor)
            .build();

    private Topic topic2 = Topic.builder()
            .topic("testTopic2")
            .topicType(topicType)
            .sensor(sensor)
            .build();

    @BeforeEach
    void setUp() {
        topicTypeRepository.save(topicType);
        sensorRepository.save(sensor);
        topicRepository.save(topic1);
        topicRepository.save(topic2);

        entityManager.clear();
    }

    @Test
    void existsTopicByTopic() {
        assertTrue(topicRepository.existsTopicByTopic("testTopic1"));
    }

    @Test
    void getAllTopics() {
        Page<TopicResponse> allTopics = topicRepository.getAllTopics(sensor.getSensorId(), null);
        assertAll(
                ()->assertEquals(2, allTopics.getTotalElements()),
                ()->assertEquals(1, allTopics.getTotalPages()),
                ()->assertTrue(allTopics.stream().map(topicResponse -> ReflectionTestUtils.getField(topicResponse,"topic")).collect(Collectors.toList()).containsAll(List.of("testTopic1", "testTopic2"))
                ));
    }

    @Test
    void getTopicBySensorBrokerBrokerId() {
        List<Topic> topics = topicRepository.getTopicBySensorBrokerBrokerId(sensor.getBroker().getBrokerId());
        assertEquals(2, topics.size());
        assertTrue(topics.stream().map(Topic::getTopic).collect(Collectors.toList()).containsAll(List.of("testTopic1", "testTopic2")));
    }

    @Test
    void findByTopicAndSensorSensorId() {
        assertNotNull(topicRepository.findByTopicAndSensorSensorId("testTopic1", sensor.getSensorId()));
    }

//    @Test
//    void getTopicByTopicTypeTopicType() {
//        List<Topic> topics = topicRepository.getTopicByTopicTypeTopicType("testTopicType");
//        assertEquals(2, topics.size());
//        assertTrue(topics.stream().map(Topic::getTopic).collect(Collectors.toList()).containsAll(List.of("testTopic1", "testTopic2")));
//    }
}