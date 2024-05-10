package live.smoothing.device.controller;

import live.smoothing.device.sensor.dto.*;
import live.smoothing.device.sensor.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/device/tags")
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<Void> addTag(@RequestHeader("X-USER-ID") String userId,
                                       @RequestBody TagRequest tagRequest) {
        tagService.addTag(userId, tagRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<TagListResponse> getTags(@RequestHeader("X-USER-ID") String userId) {
        return ResponseEntity.ok(tagService.getTags(userId));
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<Void> updateTag(@RequestHeader("X-USER-ID") String userId,
                                          @PathVariable Integer tagId,
                                          @RequestBody TagRequest tagRequest) {
        tagService.updateTag(tagId ,userId, tagRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> deleteTag(@RequestHeader("X-USER-ID") String userId,
                                          @PathVariable Integer tagId) {
        tagService.deleteTag(userId, tagId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/sensors")
    public SensorTagsResponse getSensorTags(@RequestHeader("X-USER-ID") String userId,
                                            @RequestBody SensorIdListRequest sensorIdListRequest) {
        return tagService.getSensorTags(userId, sensorIdListRequest.getSensorIds());
    }

    @PostMapping("/sensorTag")
    public void addSensorTag(@RequestHeader("X-USER-ID") String userId,
                             @RequestBody SensorTagAddRequest sensorTagAddRequest) {
        tagService.addSensorTag(userId, sensorTagAddRequest);
    }

    @DeleteMapping("/sensorTag/{sensorTagId}")
    public void deleteSensorTag(@RequestHeader("X-USER-ID") String userId,
                                @PathVariable Integer sensorTagId) {
        tagService.removeSensorTag(userId, sensorTagId);
    }

}
