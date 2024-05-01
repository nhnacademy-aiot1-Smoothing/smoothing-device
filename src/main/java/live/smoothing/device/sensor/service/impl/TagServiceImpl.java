package live.smoothing.device.sensor.service.impl;

import live.smoothing.device.sensor.dto.TopicListResponse;
import live.smoothing.device.sensor.repository.SensorTagRepository;
import live.smoothing.device.sensor.repository.TagRepository;
import live.smoothing.device.sensor.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 태그 서비스 구현체<br>
 * 태그와 관련된 비즈니스 로직을 처리한다.
 *
 * @author 우혜승
 */
@Service("tagService")
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final SensorTagRepository sensorTagRepository;

    /**
     * @inheritDoc
     */
    @Override
    public TopicListResponse getTagTopics(String userId, List<String> tags) {
        return new TopicListResponse(tagRepository.getTopicsByUserIdAndTags(userId, tags, (long) tags.size()));
    }

}
