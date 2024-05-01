package live.smoothing.device.sensor.service;

import live.smoothing.device.sensor.dto.TopicListResponse;

import java.util.List;

/**
 * 태그 서비스<br>
 * 태그와 관련된 비즈니스 로직을 처리한다.
 *
 * @author 우혜승
 */
public interface TagService {

    /**
     * 사용자의 태그를 기반으로 태그 토픽을 조회하는 메서드
     *
     * @param userId 조회할 사용자의 아이디
     * @param tags 조회할 태그 목록
     * @return 조회된 태그 토픽을 담은 응답 DTO
     */
    TopicListResponse getTagTopics(String userId, List<String> tags);
}
