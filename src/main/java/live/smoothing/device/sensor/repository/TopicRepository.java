package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.dto.TopicResponse;
import live.smoothing.device.sensor.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

    boolean existsTopicByTopic(String topic);

    @Query("SELECT new live.smoothing.device.sensor.dto.TopicResponse(t.topicId ,t.topicType.topicType, t.topic, t.sensor.sensorName) FROM Topic t WHERE t.sensor.sensorId = :sensorId")
    Page<TopicResponse> getAllTopics(Integer sensorId, Pageable pageable);

    List<Topic> getTopicBySensorBrokerBrokerId(Integer brokerId);

    Optional<Topic> findByTopicAndSensorSensorId(String topic, Integer sensorId);
}
