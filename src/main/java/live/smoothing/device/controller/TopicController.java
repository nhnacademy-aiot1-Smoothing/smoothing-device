package live.smoothing.device.controller;

import live.smoothing.device.sensor.dto.*;
import live.smoothing.device.sensor.service.TagService;
import live.smoothing.device.sensor.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/device/topics")
public class TopicController {
    private final TopicService topicService;
    private final TagService tagService;

    @PostMapping
    public ResponseEntity<Void> addTopic(@RequestBody TopicAddRequest topicAddRequest) {
        topicService.saveTopic(topicAddRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{sensorId}")
    public ResponseEntity<TopicListResponse> getTopics(@PathVariable("sensorId") Integer sensorId,
                                                       Pageable pageable) {
        return ResponseEntity.ok(topicService.getTopics(sensorId, pageable));
    }

    @PutMapping("/{topicId}")
    public ResponseEntity<Void> updateTopic(@PathVariable("topicId") Integer topicId,
                                            @RequestBody TopicUpdateRequest topicUpdateRequest) {
        topicService.updateTopic(topicId, topicUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> deleteTopic(@PathVariable("topicId") Integer topicId) {
        topicService.deleteTopic(topicId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/types")
    public ResponseEntity<TopicTypeListResponse> getTopicTypes() {
        return ResponseEntity.ok(topicService.getTopicTypes());
    }

    @GetMapping
    public ResponseEntity<TagTopicResponse> getTagTopics(@RequestParam("userId") String userId, @RequestParam("tags") List<String> tags) {
        return ResponseEntity.ok(tagService.getTagTopics(userId, tags));
    }
}
