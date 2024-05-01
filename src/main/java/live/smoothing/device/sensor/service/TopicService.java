package live.smoothing.device.sensor.service;

import live.smoothing.device.sensor.dto.TopicResponseListResponse;
import live.smoothing.device.sensor.dto.*;
import org.springframework.data.domain.Pageable;

/**
 * 토픽 서비스<br>
 * 토픽과 관련된 비즈니스 로직을 처리한다.
 *
 * @author  우혜승
 */
public interface TopicService {

    /**
     * 토픽을 저장하는 메서드
     *
     * @param topicAddRequest 저장할 토픽 정보를 담은 요청 DTO
     */
    void saveTopic(TopicAddRequest topicAddRequest);

    /**
     * 토픽 목록을 조회하는 메서드
     *
     * @param sensorId 조회할 센서의 아이디
     * @param pageable 페이지 정보
     * @return 조회된 토픽 목록을 담은 응답 DTO
     */
    TopicResponseListResponse getTopics(Integer sensorId, Pageable pageable);

    /**
     * 토픽을 수정하는 메서드
     *
     * @param topicId 수정할 토픽의 아이디
     * @param topicUpdateRequest 수정할 토픽 정보를 담은 요청 DTO
     */
    void updateTopic(Integer topicId ,TopicUpdateRequest topicUpdateRequest);

    /**
     * 토픽을 삭제하는 메서드
     *
     * @param topicId 삭제할 토픽의 아이디
     */
    void deleteTopic(Integer topicId);

    /**
     * 토픽 타입 목록을 조회하는 메서드
     *
     * @return 조회된 토픽 타입 목록을 담은 응답 DTO
     */
    TopicTypeListResponse getTopicTypes();

    /**
     * 모든 토픽 목록을 조회하는 메서드
     * @return 조회된 모든 토픽 목록을 담은 응답 DTO
     */
    TopicListResponse getAllTopics();
}
