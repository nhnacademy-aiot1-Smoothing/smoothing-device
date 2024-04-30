package live.smoothing.device.sensor.service.impl;

import live.smoothing.device.sensor.dto.TagTopicResponse;
import live.smoothing.device.sensor.repository.SensorTagRepository;
import live.smoothing.device.sensor.repository.TagRepository;
import live.smoothing.device.sensor.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tagService")
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final SensorTagRepository sensorTagRepository;

    @Override
    public TagTopicResponse getTagTopics(String userId, List<String> tags) {
        return new TagTopicResponse(tagRepository.getTopicsByUserIdAndTags(userId, tags, (long) tags.size()));
    }

}
