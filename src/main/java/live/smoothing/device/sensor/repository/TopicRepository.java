package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.dto.TopicResponse;
import live.smoothing.device.sensor.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

    boolean existsTopicByTopic(String topic);

    @Query("SELECT new live.smoothing.device.sensor.dto.TopicResponse(t.topicId ,t.topicType.topicType, t.topic, t.sensor.sensorName) FROM Topic t WHERE t.sensor.sensorId = :sensorId")
    List<TopicResponse> getAllTopics(Integer sensorId);
}
