package live.smoothing.device.controller;

import live.smoothing.device.sensor.dto.TopicResponseListResponse;
import live.smoothing.device.sensor.dto.*;
import live.smoothing.device.sensor.service.TagService;
import live.smoothing.device.sensor.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 토픽 컨트롤러<br>
 * 토픽 관련 API를 제공하는 컨트롤러
 *
 * @author 우혜승
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/device/topics")
public class TopicController {
    private final TopicService topicService;
    private final TagService tagService;

    /**
     * 토픽 추가 API
     *
     * @param topicAddRequest 토픽 추가 요청
     * @return HTTP 상태코드
     */
    @PostMapping
    public ResponseEntity<Void> addTopic(@RequestBody TopicAddRequest topicAddRequest) {
        topicService.saveTopic(topicAddRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 토픽 조회 API
     *
     * @param sensorId 센서 ID
     * @param pageable 페이징 정보
     * @return 토픽 리스트 응답 HTTP 상태코드
     */
    @GetMapping("/{sensorId}")
    public ResponseEntity<TopicResponseListResponse> getTopics(@PathVariable("sensorId") Integer sensorId,
                                                               Pageable pageable) {
        return ResponseEntity.ok(topicService.getTopics(sensorId, pageable));
    }

    /**
     * 토픽 수정 API
     *
     * @param topicId 토픽 ID
     * @param topicUpdateRequest 토픽 수정 요청
     * @return HTTP 상태코드
     */
    @PutMapping("/{topicId}")
    public ResponseEntity<Void> updateTopic(@PathVariable("topicId") Integer topicId,
                                            @RequestBody TopicUpdateRequest topicUpdateRequest) {
        topicService.updateTopic(topicId, topicUpdateRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * 토픽 삭제 API
     *
     * @param topicId 삭제할 토픽 ID
     * @return HTTP 상태코드
     */
    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> deleteTopic(@PathVariable("topicId") Integer topicId) {
        topicService.deleteTopic(topicId);
        return ResponseEntity.ok().build();
    }

    /**
     * 토픽 타입 목록 조회 API
     *
     * @return 토픽 타입 리스트 응답 HTTP 상태코드
     */
    @GetMapping("/types")
    public ResponseEntity<TopicTypeListResponse> getTopicTypes() {
        return ResponseEntity.ok(topicService.getTopicTypes());
    }

    /**
     * 토픽 태그 목록 조회 API
     *
     * @return 토픽 태그 리스트 응답 HTTP 상태코드
     */
    @GetMapping
    public ResponseEntity<TopicListResponse> getTopicsByTagsAndType(@RequestParam("userId") String userId,
                                                                    @RequestParam("tags") List<String> tags,
                                                                    @RequestParam("type") String type) {
        return ResponseEntity.ok(tagService.getTagTopics(userId, tags, type));
    }

    /**
     * 센서 타입에 대한 모든 토픽 조회 API
     *
     * @return 토픽 리스트 응답 HTTP 상태코드
     */
    @GetMapping("/all")
    public ResponseEntity<TopicListResponse> getAllTopicsByType(@RequestParam("type") String type){
        return ResponseEntity.ok(topicService.getAllTopics(type));
    }

    /**
     * 태그와 센서 타입에 대한 센서와 토픽 조회 API
     *
     * @return 센서와 토픽 리스트 응답 HTTP 상태코드
     */
    @GetMapping("/sensors")
    public ResponseEntity<SensorTopicResponse> getSensorWithTopics(@RequestParam("userId") String userId,
                                                                   @RequestParam("tags") List<String> tags,
                                                                   @RequestParam("type") String type) {
        return ResponseEntity.ok(tagService.getSensorWithTopics(userId ,tags, type));
    }
}
