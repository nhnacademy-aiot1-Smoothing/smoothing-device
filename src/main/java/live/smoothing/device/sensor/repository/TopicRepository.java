package live.smoothing.device.sensor.repository;

import live.smoothing.device.sensor.dto.SensorTopicDto;
import live.smoothing.device.sensor.dto.TopicResponse;
import live.smoothing.device.sensor.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * 토픽 레포지토리
 *
 * @author 우혜승
 */
public interface TopicRepository extends JpaRepository<Topic, Integer> {

    /**
     * 토픽 존재 여부
     *
     * @param topic 토픽
     * @return 토픽 존재 여부
     */
    boolean existsTopicByTopic(String topic);

    /**
     * 센서 아이디로 토픽 조회
     *
     * @param sensorId 센서 아이디
     * @param pageable 페이징 정보
     * @return 센서 아이디로 조회된 토픽 페이지
     */
    @Query("SELECT new live.smoothing.device.sensor.dto.TopicResponse(t.topicId ,t.topicType.topicType, t.topic, t.sensor.sensorName) FROM Topic t WHERE t.sensor.sensorId = :sensorId")
    Page<TopicResponse> getAllTopics(Integer sensorId, Pageable pageable);

    /**
     * 브로커 아이디로 토픽 조회
     *
     * @param brokerId 브로커 아이디
     * @return 브로커 아이디로 조회된 토픽 리스트
     */
    List<Topic> getTopicBySensorBrokerBrokerId(Integer brokerId);

    /**
     * 토픽과 센서 아이디로 토픽 조회
     * @param topic 토픽
     * @param sensorId 센서 아이디
     * @return 토픽과 센서 아이디로 조회된 토픽
     */
    Optional<Topic> findByTopicAndSensorSensorId(String topic, Integer sensorId);

    /**
     * 센서 타입으로 토픽 목록 조회
     *
     * @param type 센서 타입
     * @return 센서 타입으로 조회된 토픽 목록
     */
    @Query("SELECT t.topic FROM Topic t WHERE t.topicType.topicType = :type")
    List<String > getTopicByTopicType(String type);

    @Query("SELECT new live.smoothing.device.sensor.dto.SensorTopicDto(s.sensorName, t.topic) " +
            "FROM Topic t " +
            "JOIN t.sensor s " +
            "WHERE t.topicType.topicType = :type")
    List<SensorTopicDto> getSensorTopicsByTopicType(String type);
}
