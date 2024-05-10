package live.smoothing.device.sensor.service;

import live.smoothing.device.sensor.dto.*;

import java.util.List;

/**
 * 태그 서비스<br>
 * 태그와 관련된 비즈니스 로직을 처리한다.
 *
 * @author 우혜승
 */
public interface TagService {

    /**
     * 사용자의 태그, 타입을 기반으로 태그 토픽을 조회하는 메서드
     *
     * @param userId 사용자 아이디
     * @param tags 조회할 태그 목록
     * @param type 조회할 태그 토픽의 타입
     * @return 조회된 태그 토픽을 담은 응답 DTO
     */
    TopicListResponse getTagTopics(String userId ,List<String> tags, String type);

    /**
     * 사용자의 태그, 타입을 기반으로 센서와 태그 토픽을 조회하는 메서드
     *
     * @param userId 사용자 아이디
     * @param tags 조회할 태그 목록
     * @param type 조회할 태그 토픽의 타입
     * @return
     */
    SensorTopicResponse getSensorWithTopics(String userId ,List<String> tags, String type);

    /**
     * 태그를 등록하는 메서드
     *
     * @param userId 사용자 아이디
     * @param tagRequest 태그 등록 요청 DTO
     */
    void addTag(String userId, TagRequest tagRequest);

    /**
     * 사용자의 태그 목록을 조회하는 메서드
     *
     * @param userId 사용자 아이디
     * @return 사용자의 태그 목록을 담은 응답 DTO
     */
    TagListResponse getTags(String userId);

    /**
     * 태그를 수정하는 메서드
     *
     * @param userId 사용자 아이디
     * @param tagRequest 태그 수정 요청 DTO
     */
    void updateTag(Integer tagId, String userId, TagRequest tagRequest);

    void deleteTag(String userId, Integer tagId);

    SensorTagsResponse getSensorTags(String userId, List<Integer> sensorIds);

    void addSensorTag(String userId, SensorTagAddRequest sensorTagAddRequest);

    void removeSensorTag(String userId, Integer sensorTagId);
}
