package live.smoothing.device.sensor.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import live.smoothing.device.sensor.dao.SensorTagDao;
import live.smoothing.device.sensor.dto.SensorTopicDto;
import live.smoothing.device.sensor.entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class CustomTagRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @SpyBean
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private CustomTagRepository customTagRepository;

    private final String userId = "testUserId";

    private final SensorType sensorType = new SensorType("testSensorType");

    private final Sensor sensor = Sensor.builder()
            .sensorName("testSensorName")
            .sensorType(sensorType)
            .sensorRegisteredAt(LocalDateTime.now())
            .build();

    private final Tag tag = Tag.builder()
            .tagName("testTagName")
            .userId(userId)
            .build();

    private final SensorTag sensorTag = SensorTag.builder()
            .sensor(sensor)
            .tag(tag)
            .build();

    private final TopicType topicType = new TopicType("testTopicType");

    private final Topic topic = Topic.builder()
            .topic("testTopic")
            .topicType(topicType)
            .sensor(sensor)
            .build();


    @BeforeEach
    void setUp() {
        entityManager.persist(sensorType);
        entityManager.persist(sensor);
        entityManager.persist(tag);
        entityManager.persist(sensorTag);
        entityManager.persist(topicType);
        entityManager.persist(topic);
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    void getSensorTags() {
        List<SensorTagDao> sensorTags = customTagRepository.getSensorTags(userId, List.of(1));

        assertEquals(1, sensorTags.size());
        assertEquals(1, sensorTags.get(0).getSensorId());
        assertEquals(1, sensorTags.get(0).getTagId());
        assertEquals(tag.getTagName(), sensorTags.get(0).getTagName());
    }

    @Test
    void getSensorTopicsByTagsAndType() {
        List<SensorTopicDto> sensorTopicDtos = customTagRepository.getSensorTopicsByTagsAndType(userId, List.of(tag.getTagName()), topicType.getTopicType(), 1L);

        assertEquals(1, sensorTopicDtos.size());
        assertEquals(sensor.getSensorName(), sensorTopicDtos.get(0).getSensorName());
        assertEquals(topic.getTopic(), sensorTopicDtos.get(0).getTopic());
    }

    @Test
    void getTopicsByUserIdAndTags() {
        List<String> topics = customTagRepository.getTopicsByUserIdAndTags(userId, List.of(tag.getTagName()), 1L, topicType.getTopicType());

        assertEquals(1, topics.size());
        assertEquals(topic.getTopic(), topics.get(0));
    }
}