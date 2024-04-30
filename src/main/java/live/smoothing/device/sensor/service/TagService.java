package live.smoothing.device.sensor.service;

import live.smoothing.device.sensor.dto.TagTopicResponse;

import java.util.List;

public interface TagService {
    TagTopicResponse getTagTopics(String userId, List<String> tags);
}
