package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.dto.TopicTypeListResponse;
import live.smoothing.device.sensor.dto.TopicTypeResponse;
import live.smoothing.device.sensor.entity.TopicType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicTypeRepository extends JpaRepository<TopicType, String> {

    @Query("SELECT new live.smoothing.device.sensor.dto.TopicTypeResponse(t.topicType) FROM TopicType t")
    List<TopicTypeResponse> getAllTopicTypes();
}
