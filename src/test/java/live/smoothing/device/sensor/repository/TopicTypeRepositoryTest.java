package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.dto.TopicTypeResponse;
import live.smoothing.device.sensor.entity.TopicType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TopicTypeRepositoryTest {

    @Autowired
    private TopicTypeRepository topicTypeRepository;

    private TopicType topicType = new TopicType("testTopicType");

    @BeforeEach
    void setUp() {
        topicTypeRepository.save(topicType);
    }

    @Test
    void getAllTopicTypes() {
        List<TopicTypeResponse> topicTypeResponses = topicTypeRepository.getAllTopicTypes();

        assertAll(
                ()-> assertNotNull(topicTypeResponses),
                ()-> assertEquals(1, topicTypeResponses.size()),
                ()-> assertEquals(topicType.getTopicType(), ReflectionTestUtils.getField(topicTypeResponses.get(0), "topicType"))
        );
    }
}