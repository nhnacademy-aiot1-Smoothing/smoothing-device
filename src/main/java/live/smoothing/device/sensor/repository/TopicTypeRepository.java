package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.dto.TopicTypeResponse;
import live.smoothing.device.sensor.entity.TopicType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 토픽 타입 레포지토리
 *
 * @author 우혜승
 */
public interface TopicTypeRepository extends JpaRepository<TopicType, String> {

    /**
     * 모든 토픽 타입 조회
     *
     * @return 모든 토픽 타입 목록
     */
    @Query("SELECT new live.smoothing.device.sensor.dto.TopicTypeResponse(t.topicType) FROM TopicType t")
    List<TopicTypeResponse> getAllTopicTypes();
}
