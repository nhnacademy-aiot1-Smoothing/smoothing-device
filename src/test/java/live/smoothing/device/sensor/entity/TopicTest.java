package live.smoothing.device.sensor.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TopicTest {
    private final Topic topic = Topic.builder()
            .topicRegisteredAt(LocalDateTime.now())
            .topicType(new TopicType())
            .build();

    @Test
    void getTopicRegisteredAt() {
        assertNotNull(topic.getTopicRegisteredAt());
    }

    @Test
    void getTopicType() {
        assertNotNull(topic.getTopicType());
    }
}